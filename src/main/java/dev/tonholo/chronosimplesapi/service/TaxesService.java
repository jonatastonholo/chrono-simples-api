package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.BaseTaxes;
import dev.tonholo.chronosimplesapi.domain.FinancialDependent;
import dev.tonholo.chronosimplesapi.domain.WorkedHours;
import dev.tonholo.chronosimplesapi.exception.ApiInternalException;
import dev.tonholo.chronosimplesapi.repository.postgres.FinancialDependentRepository;
import dev.tonholo.chronosimplesapi.repository.postgres.PeriodRepository;
import dev.tonholo.chronosimplesapi.service.event.TaxCalculationEvent;
import dev.tonholo.chronosimplesapi.service.event.TaxCalculationResultEvent;
import dev.tonholo.chronosimplesapi.service.validation.TaxCalculationEventValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.math.RoundingMode.HALF_UP;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaxesService {
    private static final BigDecimal INSS_DEFAULT_PERCENTAGE = BigDecimal.valueOf(0.11);
    private static final BigDecimal IRRF_MINIMUM_AMOUNT = BigDecimal.valueOf(10.00).setScale(2,HALF_UP);
    private final BaseTaxes baseTaxes;
    private final TaxCalculationEventValidation taxCalculationEventValidation;
    private final PeriodRepository periodRepository;
    private final FinancialDependentRepository financialDependentRepository;

    public Mono<TaxCalculationResultEvent> calculate(TaxCalculationEvent taxCalculationEvent) {
        log.info("Calculating taxes -> {}", taxCalculationEvent);
        final TaxCalculationResultEvent taxCalculationResultEvent = new TaxCalculationResultEvent();
        taxCalculationResultEvent.setPeriodBegin(taxCalculationEvent.getPeriodBegin());
        taxCalculationResultEvent.setPeriodEnd(taxCalculationEvent.getPeriodEnd());

        return Mono.just(taxCalculationEvent)
                .doOnNext(taxCalculationEventValidation::validate)
                .flatMap(this::calculatePeriodEarnings)
                .flatMap(periodEarnings -> {
                            taxCalculationResultEvent.setPeriodEarnings(periodEarnings);
                            return calculateBaseProLabor(taxCalculationEvent.getRFactor().orElse(null), periodEarnings);
                        })
                .flatMap(baseProLabor -> {
                    taxCalculationResultEvent.setBaseProLabor(baseProLabor);
                    taxCalculationResultEvent
                            .setRFactor(taxCalculationEvent.getRFactor()
                                    .orElse(baseTaxes.getDefaultRFactor()));
                    return countFinancialDependents(taxCalculationEvent);
                })
                .flatMap(financialDependentsCount -> {
                    taxCalculationResultEvent.setFinancialDependents(financialDependentsCount);
                    return calculateFinancialDependentsDeduction(taxCalculationEvent);
                })
                .flatMap(financialDependentsDeduction -> {
                    taxCalculationResultEvent.setFinancialDependentsDeduction(financialDependentsDeduction);
                    return calculateInssAmount(INSS_DEFAULT_PERCENTAGE, taxCalculationResultEvent.getBaseProLabor());
                })
                .flatMap(inssAmount -> {
                    taxCalculationResultEvent.setInssAmount(inssAmount);

                    return calculateIrrfAmount(taxCalculationResultEvent.getBaseProLabor(),
                            taxCalculationResultEvent.getInssAmount(),
                            taxCalculationResultEvent.getFinancialDependentsDeduction());
                })
                .flatMap(irrfAmount -> {
                    taxCalculationResultEvent.setIrrfAmount(irrfAmount);
                    return calculateLast12MonthsEarnings(taxCalculationEvent.getPeriodBegin());
                })
                .flatMap(last12MonthEarnings -> {
                    taxCalculationResultEvent.setLast12MonthEarnings(last12MonthEarnings);
                    return calculateDasAmount(last12MonthEarnings, taxCalculationResultEvent.getPeriodEarnings());
                })
                .flatMap(dasTaxAmount -> {
                    taxCalculationResultEvent.setDasAmount(dasTaxAmount);
                    return calculateWorkedHours(taxCalculationEvent.getPeriodBegin(), taxCalculationEvent.getPeriodEnd());
                })
                .map(workedHours -> {
                    taxCalculationResultEvent.setWorkedHours(workedHours);
                    return taxCalculationResultEvent;
                });
    }

    public Mono<BigDecimal> calculatePeriodEarnings(TaxCalculationEvent taxCalculationEvent) {
        log.info("Calculating period earnings");
        return periodRepository.findByDateRange(taxCalculationEvent.getPeriodBegin(), taxCalculationEvent.getPeriodEnd())
                .reduce(BigDecimal.ZERO.setScale(2, HALF_UP), (accumulator, period)
                        -> accumulator.add(period.getAmount()));
    }

    public Mono<BigDecimal> calculateBaseProLabor(BigDecimal rFactor, BigDecimal periodEarnings) {
        final var rFactorToUse = rFactor == null
                ? baseTaxes.getDefaultRFactor()
                : rFactor;
        log.info("Calculating base pro-labor. rFactor: {} | periodEarnings: {}", rFactorToUse, periodEarnings);
        return Mono.just(
                periodEarnings
                        .multiply(rFactorToUse).setScale(2, HALF_UP));
    }

    public Mono<Integer> countFinancialDependents(TaxCalculationEvent taxCalculationEvent) {
        return financialDependentRepository
                .countByPeriodRange(taxCalculationEvent.getPeriodBegin().toLocalDate(), taxCalculationEvent.getPeriodEnd().toLocalDate());
    }

    public Mono<BigDecimal> calculateFinancialDependentsDeduction(TaxCalculationEvent taxCalculationEvent) {
        return financialDependentRepository
                .findByPeriodRange(taxCalculationEvent.getPeriodBegin().toLocalDate(), taxCalculationEvent.getPeriodEnd().toLocalDate())
                .filter(FinancialDependent::getIrrfDeduct)
                .reduce(BigDecimal.ZERO.setScale(2, HALF_UP), (accumulator, unused)
                        -> accumulator.add(baseTaxes.getDeductionAmountPerFinancialDependent()));
    }

    public Mono<BigDecimal> calculateInssAmount(BigDecimal inssBasePercentage, BigDecimal baseProLabor) {
        return Flux.fromIterable(baseTaxes.getInssBases())
                .filter(inssBase
                        -> inssBase.getBasePercentage().compareTo(inssBasePercentage) == 0)
                .last()
                .map(inssBase -> {
                    if (baseProLabor.compareTo(baseTaxes.getPensionCeiling()) > 0) {
                        log.info("Calculating INSS Amount based on Pension Ceiling {} | INSS percentage {}", baseTaxes.getPensionCeiling(), inssBase.getBasePercentage());
                        return inssBase.getBaseValueRangeEnd()
                                .multiply(inssBase.getBasePercentage())
                                .setScale(2, HALF_UP);
                    }

                    log.info("Calculating INSS based on Pro-Labor {} | INSS percentage {}", baseProLabor, inssBase.getBasePercentage());
                    return baseProLabor
                            .multiply(inssBase.getBasePercentage())
                            .setScale(2, HALF_UP);
                });
    }

    public Mono<BigDecimal> calculateIrrfAmount(BigDecimal baseProLaborAmount,
                                                BigDecimal calculatedInssAmount,
                                                BigDecimal calculatedFinancialDependentsDeductionAmount) {
        log.info("Calculating IRRF Tax");
        final var baseIrrf =
                baseProLaborAmount
                .subtract(calculatedInssAmount)
                .subtract(calculatedFinancialDependentsDeductionAmount);

        BigDecimal aliquot = null;
        BigDecimal irrfDeduction = null;
        for (var irrfBase: baseTaxes.getIrrfBases()) {
            if (baseIrrf.compareTo(irrfBase.getBaseValueRangeBegin()) >= 0
                    && baseIrrf.compareTo(irrfBase.getBaseValueRangeEnd()) <= 0) {
                aliquot = irrfBase.getAliquot();
                irrfDeduction = irrfBase.getDeduction();
                log.info("IRRF aliquot {} and deduction {} selected", aliquot, irrfDeduction);
                break;
            }
        }

        if (Objects.isNull(aliquot) || Objects.isNull(irrfDeduction)) {
            throw new ApiInternalException("There was a problem calculating the IRRF. It was not possible to find the base values of IRRF.");
        }

        final var irrfAmount =
                baseIrrf
                .multiply(aliquot)
                .subtract(irrfDeduction)
                .setScale(2, HALF_UP);

        final var realIrrfAmount =
            irrfAmount.compareTo(IRRF_MINIMUM_AMOUNT) > 0
                    ? irrfAmount
                    : BigDecimal.ZERO;

        return Mono.just(realIrrfAmount.setScale(2, HALF_UP));
    }

    public Mono<BigDecimal> calculateLast12MonthsEarnings(LocalDateTime periodBegin) {
        log.info("Calculating Last 12 months Earning counting from {}", periodBegin);
        final var aYearAgo = periodBegin.plusMonths(-12);
        return periodRepository.findByDateRange(aYearAgo, periodBegin)
                .reduce(BigDecimal.ZERO.setScale(2, HALF_UP), (accumulator, period)
                        -> accumulator.add(period.getAmount()));
    }

    public Mono<BigDecimal> calculateDasAmount(BigDecimal last12MonthEarnings, BigDecimal periodEarnings) {
        log.info("Calculating DAS Tax");
        BigDecimal aliquot = null;
        BigDecimal dasDeduction = null;
        for (var dasBase: baseTaxes.getDasBases()) {
            if (last12MonthEarnings.compareTo(dasBase.getBaseValueRangeBegin()) >= 0
                    && last12MonthEarnings.compareTo(dasBase.getBaseValueRangeEnd()) <= 0) {
                aliquot = dasBase.getAliquot();
                dasDeduction = dasBase.getDeduction();
                log.info("Das aliquot {} and deduction {} selected", aliquot, dasDeduction);
                break;
            }
        }

        if (Objects.isNull(aliquot) || Objects.isNull(dasDeduction)) {
            throw new ApiInternalException("An error occurs on DAS Tax calculation -> the aliquot or das deduction was not found");
        }

        return Mono.just(periodEarnings
                .multiply(aliquot)
                .subtract(dasDeduction)
                .setScale(2, HALF_UP));
    }

    public Mono<WorkedHours> calculateWorkedHours(LocalDateTime begin, LocalDateTime end) {
        log.info("Calculating period earnings");
        return periodRepository.findByDateRange(begin, end)
                .reduce(0L, (accumulator, period)
                        -> accumulator + Duration.between(period.getBegin(), period.getEnd()).toMillis())
                .map(timeElapsedInMillis -> {
                    final var hours = TimeUnit.MILLISECONDS.toHours(timeElapsedInMillis);
                    final var minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsedInMillis) % 60;
                    final var seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsedInMillis)  % 60;

                    return WorkedHours.builder()
                            .hours(hours)
                            .minutes(minutes)
                            .seconds(seconds)
                            .timeElapsedInMillis(timeElapsedInMillis)
                            .build();
                });
    }
}
