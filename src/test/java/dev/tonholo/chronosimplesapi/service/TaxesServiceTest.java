package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.BaseTaxes;
import dev.tonholo.chronosimplesapi.domain.WorkedHours;
import dev.tonholo.chronosimplesapi.repository.postgres.FinancialDependentRepository;
import dev.tonholo.chronosimplesapi.repository.postgres.PeriodRepository;
import dev.tonholo.chronosimplesapi.service.event.TaxCalculationEvent;
import dev.tonholo.chronosimplesapi.service.validation.TaxCalculationEventValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static dev.tonholo.chronosimplesapi.mock.model.FinancialDependentMock.getFinancialDependentMock;
import static dev.tonholo.chronosimplesapi.mock.model.InssBaseMock.getInssBaseMocks;
import static dev.tonholo.chronosimplesapi.mock.model.IrrfBaseMock.getIrrfBaseMocks;
import static dev.tonholo.chronosimplesapi.mock.model.PeriodMock.getPeriodMock;
import static java.math.RoundingMode.HALF_UP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TaxesServiceTest {

    private TaxesService taxesService;
    private static final BigDecimal DEFAULT_R_FACTOR = BigDecimal.valueOf(0.28);
    private static final BigDecimal DEDUCTION_AMOUNT_PER_FINANCIAL_DEPENDENT = BigDecimal.valueOf(189.59);
    private static final BigDecimal INSS_DEFAULT_PERCENTAGE = BigDecimal.valueOf(0.11);
    private static final BigDecimal PENSION_CEILING = BigDecimal.valueOf(5645.8).setScale(2, HALF_UP);
    private static final BigDecimal INSS_MAX_AMOUNT = BigDecimal.valueOf(642.34).setScale(2, HALF_UP);

    @Mock private BaseTaxes baseTaxes;
    @Mock private TaxCalculationEventValidation taxCalculationEventValidation;
    @Mock private PeriodRepository periodRepository;
    @Mock private FinancialDependentRepository financialDependentRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        taxesService = new TaxesService(
                baseTaxes,
                taxCalculationEventValidation,
                periodRepository,
                financialDependentRepository);
    }

    @Test
    @DisplayName("Given 4 periods with 8H each, with 90.00 hour value, calculate the period amount value 2880.00")
    void calculatePeriodEarningsTest01() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);
        final var periods = List.of(
                getPeriodMock(90.0, begin, 8),
                getPeriodMock(90.0, begin.plusDays(1), 8),
                getPeriodMock(90.0, begin.plusDays(2), 8),
                getPeriodMock(90.0, begin.plusDays(3), 8));

        final var amountToCheck = BigDecimal.valueOf(2880).setScale(2, HALF_UP);

        final var taxCalculationEvent = TaxCalculationEvent.builder()
                .periodBegin(begin)
                .periodEnd(end)
                .build();

        when(periodRepository.findByDateRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Flux.fromIterable(periods));

        StepVerifier.create(taxesService.calculatePeriodEarnings(taxCalculationEvent))
                .assertNext(amount -> assertEquals(amountToCheck, amount))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given no periods, with 90.00 hour value, calculate the period amount value 0.00")
    void calculatePeriodEarningsTest02() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);

        final var amountToCheck = BigDecimal.valueOf(0).setScale(2, HALF_UP);

        final var taxCalculationEvent = TaxCalculationEvent.builder()
                .periodBegin(begin)
                .periodEnd(end)
                .build();

        when(periodRepository.findByDateRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Flux.empty());

        StepVerifier.create(taxesService.calculatePeriodEarnings(taxCalculationEvent))
                .assertNext(amount -> assertEquals(amountToCheck, amount))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a 28% R Factor and a 15500 period earnings, returns a 4340 pro-labor")
    void calculateBaseProLaborTest01() {
        final var periodEarnings = BigDecimal.valueOf(15500);
        final var rFactor = BigDecimal.valueOf(0.28);
        final var proLaborToCheck = BigDecimal.valueOf(4340).setScale(2, HALF_UP);

        StepVerifier.create(taxesService.calculateBaseProLabor(rFactor, periodEarnings))
                .assertNext(proLabor -> assertEquals(proLaborToCheck, proLabor))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a null R Factor and a 15500 period earnings, returns a 4340 pro-labor")
    void calculateBaseProLaborTest02() {
        final var periodEarnings = BigDecimal.valueOf(15500);
        final var proLaborToCheck = BigDecimal.valueOf(4340).setScale(2, HALF_UP);

        when(baseTaxes.getDefaultRFactor())
                .thenReturn(DEFAULT_R_FACTOR);

        StepVerifier.create(taxesService.calculateBaseProLabor(null, periodEarnings))
                .assertNext(proLabor -> assertEquals(proLaborToCheck, proLabor))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a 30% R Factor and a 15500 period earnings, returns a 4650 pro-labor")
    void calculateBaseProLaborTest03() {
        final var periodEarnings = BigDecimal.valueOf(15500);
        final var rFactor = BigDecimal.valueOf(0.30);
        final var proLaborToCheck = BigDecimal.valueOf(4650).setScale(2, HALF_UP);

        StepVerifier.create(taxesService.calculateBaseProLabor(rFactor, periodEarnings))
                .assertNext(proLabor -> assertEquals(proLaborToCheck, proLabor))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a list with 1 financial dependent, calculate deduction value of 189.59")
    void calculateFinancialDependentsDeductionTest01() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);
        final var taxCalculationEvent = TaxCalculationEvent.builder()
                .periodBegin(begin)
                .periodEnd(end)
                .build();

        final var financialDependents = List.of(
                getFinancialDependentMock("Joao", true)
        );

        final var valueToDeductCheck = BigDecimal.valueOf(189.59);

        when(financialDependentRepository.findByPeriodRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Flux.fromIterable(financialDependents));

        when(baseTaxes.getDeductionAmountPerFinancialDependent())
                .thenReturn(BigDecimal.valueOf(189.59));

        StepVerifier.create(taxesService.calculateFinancialDependentsDeduction(taxCalculationEvent))
                .assertNext(valueToDeduct -> assertEquals(valueToDeductCheck, valueToDeduct))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a list with 2 financial dependent, calculate deduction value of 379.18")
    void calculateFinancialDependentsDeductionTest02() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);
        final var taxCalculationEvent = TaxCalculationEvent.builder()
                .periodBegin(begin)
                .periodEnd(end)
                .build();

        final var financialDependents = List.of(
                getFinancialDependentMock("Joao", true),
                getFinancialDependentMock("Maria", true)
        );

        final var valueToDeductCheck = BigDecimal.valueOf(379.18);

        when(financialDependentRepository.findByPeriodRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Flux.fromIterable(financialDependents));

        when(baseTaxes.getDeductionAmountPerFinancialDependent())
                .thenReturn(BigDecimal.valueOf(189.59));

        StepVerifier.create(taxesService.calculateFinancialDependentsDeduction(taxCalculationEvent))
                .assertNext(valueToDeduct -> assertEquals(valueToDeductCheck, valueToDeduct))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a empty list of financial dependent, calculate deduction value of 0")
    void calculateFinancialDependentsDeductionTest03() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);
        final var taxCalculationEvent = TaxCalculationEvent.builder()
                .periodBegin(begin)
                .periodEnd(end)
                .build();

        final var valueToDeductCheck = BigDecimal.valueOf(0).setScale(2, HALF_UP);

        when(financialDependentRepository.findByPeriodRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Flux.empty());

        when(baseTaxes.getDeductionAmountPerFinancialDependent())
                .thenReturn(DEDUCTION_AMOUNT_PER_FINANCIAL_DEPENDENT);

        StepVerifier.create(taxesService.calculateFinancialDependentsDeduction(taxCalculationEvent))
                .assertNext(valueToDeduct -> assertEquals(valueToDeductCheck, valueToDeduct))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a list with 2 of financial dependent, only one is for irrf deduction, calculate deduction value of 189.59")
    void calculateFinancialDependentsDeductionTest04() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);
        final var taxCalculationEvent = TaxCalculationEvent.builder()
                .periodBegin(begin)
                .periodEnd(end)
                .build();

        final var valueToDeductCheck = BigDecimal.valueOf(189.59).setScale(2, HALF_UP);

        final var financialDependents = List.of(
                getFinancialDependentMock("Joao", true),
                getFinancialDependentMock("Maria", false)
        );

        when(financialDependentRepository.findByPeriodRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Flux.fromIterable(financialDependents));

        when(baseTaxes.getDeductionAmountPerFinancialDependent())
                .thenReturn(DEDUCTION_AMOUNT_PER_FINANCIAL_DEPENDENT);

        StepVerifier.create(taxesService.calculateFinancialDependentsDeduction(taxCalculationEvent))
                .assertNext(valueToDeduct -> assertEquals(valueToDeductCheck, valueToDeduct))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a list with 2 of financial dependent, no one is for irrf deduction, calculate deduction value of 0.00")
    void calculateFinancialDependentsDeductionTest05() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);
        final var taxCalculationEvent = TaxCalculationEvent.builder()
                .periodBegin(begin)
                .periodEnd(end)
                .build();

        final var valueToDeductCheck = BigDecimal.valueOf(0).setScale(2, HALF_UP);

        final var financialDependents = List.of(
                getFinancialDependentMock("Joao", false),
                getFinancialDependentMock("Maria", false)
        );

        when(financialDependentRepository.findByPeriodRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Flux.fromIterable(financialDependents));

        when(baseTaxes.getDeductionAmountPerFinancialDependent())
                .thenReturn(DEDUCTION_AMOUNT_PER_FINANCIAL_DEPENDENT);

        StepVerifier.create(taxesService.calculateFinancialDependentsDeduction(taxCalculationEvent))
                .assertNext(valueToDeduct -> assertEquals(valueToDeductCheck, valueToDeduct))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a 5565.29 Pro-Labor, return a 612.18 INSS Amount Tax")
    void calculateInssAmountTest01() {
        final var inssBases = getInssBaseMocks();
        final var proLabor = BigDecimal.valueOf(5565.29);
        final var inssAmountToCheck = BigDecimal.valueOf(612.18);

        when(baseTaxes.getInssBases())
                .thenReturn(inssBases);

        when(baseTaxes.getPensionCeiling())
                .thenReturn(PENSION_CEILING);

        StepVerifier.create(taxesService.calculateInssAmount(INSS_DEFAULT_PERCENTAGE, proLabor))
                .assertNext(inssAmount -> assertEquals(inssAmountToCheck, inssAmount))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a 5645.80 Pro-Labor, return a 621.04 INSS Amount Tax")
    void calculateInssAmountTest02() {
        final var inssBases = getInssBaseMocks();
        final var proLabor = BigDecimal.valueOf(5645.80);
        final var inssAmountToCheck = BigDecimal.valueOf(621.04);

        when(baseTaxes.getInssBases())
                .thenReturn(inssBases);

        when(baseTaxes.getPensionCeiling())
                .thenReturn(PENSION_CEILING);

        StepVerifier.create(taxesService.calculateInssAmount(INSS_DEFAULT_PERCENTAGE, proLabor))
                .assertNext(inssAmount -> assertEquals(inssAmountToCheck, inssAmount))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a 5645.81 Pro-Labor, return a 642.34 INSS Amount Tax")
    void calculateInssAmountTest03() {
        final var inssBases = getInssBaseMocks();
        final var proLabor = BigDecimal.valueOf(5645.81);
        final var inssAmountToCheck = INSS_MAX_AMOUNT;

        when(baseTaxes.getInssBases())
                .thenReturn(inssBases);

        when(baseTaxes.getPensionCeiling())
                .thenReturn(PENSION_CEILING);

        StepVerifier.create(taxesService.calculateInssAmount(INSS_DEFAULT_PERCENTAGE, proLabor))
                .assertNext(inssAmount -> assertEquals(inssAmountToCheck, inssAmount))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a 10500.00 Pro-Labor, return a 642.34 INSS Amount Tax")
    void calculateInssAmountTest04() {
        final var inssBases = getInssBaseMocks();
        final var proLabor = BigDecimal.valueOf(10500.00);
        final var inssAmountToCheck = INSS_MAX_AMOUNT;

        when(baseTaxes.getInssBases())
                .thenReturn(inssBases);

        when(baseTaxes.getPensionCeiling())
                .thenReturn(PENSION_CEILING);

        StepVerifier.create(taxesService.calculateInssAmount(INSS_DEFAULT_PERCENTAGE, proLabor))
                .assertNext(inssAmount -> assertEquals(inssAmountToCheck, inssAmount))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a 5565.29 Pro-Labor, 612.18 INSS Tax Amount, 189.59 Financial Dependent Deduction, then returns 440.61 IRRF Tax Amount")
    void calculateIrrfAmountTest01() {
        final var proLabor = BigDecimal.valueOf(5565.29);
        final var inssTaxAmount = BigDecimal.valueOf(612.18);
        final var financialDependentDeductionAmount = BigDecimal.valueOf(189.59);
        final var irrfBases = getIrrfBaseMocks();
        final var irrfTaxAmountToCheck = BigDecimal.valueOf(440.61);

        when(baseTaxes.getIrrfBases())
                .thenReturn(irrfBases);

        StepVerifier.create(taxesService.calculateIrrfAmount(proLabor, inssTaxAmount, financialDependentDeductionAmount))
                .assertNext(irrfTaxAmount -> assertEquals(irrfTaxAmountToCheck, irrfTaxAmount))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a 4175.73 Pro-Labor, 459.33 INSS Tax Amount, 189.59 Financial Dependent Deduction, then returns 440.61 IRRF Tax Amount")
    void calculateIrrfAmountTest02() {
        final var proLabor = BigDecimal.valueOf(4175.73);
        final var inssTaxAmount = BigDecimal.valueOf(459.33);
        final var financialDependentDeductionAmount = BigDecimal.valueOf(189.59);
        final var irrfBases = getIrrfBaseMocks();
        final var irrfTaxAmountToCheck = BigDecimal.valueOf(174.22);

        when(baseTaxes.getIrrfBases())
                .thenReturn(irrfBases);

        StepVerifier.create(taxesService.calculateIrrfAmount(proLabor, inssTaxAmount, financialDependentDeductionAmount))
                .assertNext(irrfTaxAmount -> assertEquals(irrfTaxAmountToCheck, irrfTaxAmount))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a 2282.17 Pro-Labor, 251.04 INSS Tax Amount, 189.59 Financial Dependent Deduction, then returns 0.00 IRRF Tax Amount")
    void calculateIrrfAmountTest03() {
        final var proLabor = BigDecimal.valueOf(2282.17);
        final var inssTaxAmount = BigDecimal.valueOf(251.04);
        final var financialDependentDeductionAmount = BigDecimal.valueOf(189.59);
        final var irrfBases = getIrrfBaseMocks();
        final var irrfTaxAmountToCheck = BigDecimal.valueOf(0.00).setScale(2, HALF_UP);

        when(baseTaxes.getIrrfBases())
                .thenReturn(irrfBases);

        StepVerifier.create(taxesService.calculateIrrfAmount(proLabor, inssTaxAmount, financialDependentDeductionAmount))
                .assertNext(irrfTaxAmount -> assertEquals(irrfTaxAmountToCheck, irrfTaxAmount))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a period range with 32 hours 10 minutes and 20 seconds worked, return the Worked Hour object")
    void calculateWorkedHoursTest01() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);
        final var periods = List.of(
                getPeriodMock(90.0, begin, 8),
                getPeriodMock(90.0, begin.plusDays(1), 8),
                getPeriodMock(90.0, begin.plusDays(2), 8),
                getPeriodMock(90.0, begin.plusDays(3), 8, 10, 20));

        final var workedHoursToCheck = WorkedHours.builder()
                .hours(32L)
                .minutes(10L)
                .seconds(20L)
                .build();

        when(periodRepository.findByDateRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Flux.fromIterable(periods));

        StepVerifier.create(taxesService.calculateWorkedHours(begin, LocalDateTime.now()))
                .assertNext(workedHours -> {
                    assertEquals(workedHoursToCheck.getHours(), workedHours.getHours());
                    assertEquals(workedHoursToCheck.getMinutes(), workedHours.getMinutes());
                    assertEquals(workedHoursToCheck.getSeconds(), workedHours.getSeconds());
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a period range with 160 hours 59 minutes and 59 seconds worked, return the Worked Hour object")
    void calculateWorkedHoursTest02() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);
        final var periods = List.of(
                getPeriodMock(90.0, begin.plusDays(1), 8),
                getPeriodMock(90.0, begin.plusDays(2), 8),
                getPeriodMock(90.0, begin.plusDays(3), 8),
                getPeriodMock(90.0, begin.plusDays(4), 8),
                getPeriodMock(90.0, begin.plusDays(5), 8),
                getPeriodMock(90.0, begin.plusDays(6), 8),
                getPeriodMock(90.0, begin.plusDays(7), 8),
                getPeriodMock(90.0, begin.plusDays(8), 8),
                getPeriodMock(90.0, begin.plusDays(9), 8),
                getPeriodMock(90.0, begin.plusDays(10), 8),
                getPeriodMock(90.0, begin.plusDays(11), 8),
                getPeriodMock(90.0, begin.plusDays(12), 8),
                getPeriodMock(90.0, begin.plusDays(13), 8),
                getPeriodMock(90.0, begin.plusDays(14), 8),
                getPeriodMock(90.0, begin.plusDays(15), 8),
                getPeriodMock(90.0, begin.plusDays(16), 8),
                getPeriodMock(90.0, begin.plusDays(17), 8),
                getPeriodMock(90.0, begin.plusDays(18), 8),
                getPeriodMock(90.0, begin.plusDays(19), 8),
                getPeriodMock(90.0, begin.plusDays(20), 8, 59, 59));

        final var workedHoursToCheck = WorkedHours.builder()
                .hours(160L)
                .minutes(59L)
                .seconds(59L)
                .build();

        when(periodRepository.findByDateRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Flux.fromIterable(periods));

        StepVerifier.create(taxesService.calculateWorkedHours(begin, LocalDateTime.now()))
                .assertNext(workedHours -> {
                    assertEquals(workedHoursToCheck.getHours(), workedHours.getHours());
                    assertEquals(workedHoursToCheck.getMinutes(), workedHours.getMinutes());
                    assertEquals(workedHoursToCheck.getSeconds(), workedHours.getSeconds());
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Given a period range with 160 hours 59 minutes and 59 seconds worked, return the Worked Hour object")
    void calculateWorkedHoursTest03() {
        final var begin = LocalDateTime.of(2021, 11, 27, 12,0,0, 0);
        final var end = begin.plusHours(160);
        final var periods = List.of(
                getPeriodMock(90.0, begin.plusDays(1), 8),
                getPeriodMock(90.0, begin.plusDays(2), 8),
                getPeriodMock(90.0, begin.plusDays(3), 8),
                getPeriodMock(90.0, begin.plusDays(4), 8),
                getPeriodMock(90.0, begin.plusDays(5), 8),
                getPeriodMock(90.0, begin.plusDays(6), 8),
                getPeriodMock(90.0, begin.plusDays(7), 8),
                getPeriodMock(90.0, begin.plusDays(8), 8),
                getPeriodMock(90.0, begin.plusDays(9), 8),
                getPeriodMock(90.0, begin.plusDays(10), 8),
                getPeriodMock(90.0, begin.plusDays(11), 8),
                getPeriodMock(90.0, begin.plusDays(12), 8),
                getPeriodMock(90.0, begin.plusDays(13), 8),
                getPeriodMock(90.0, begin.plusDays(14), 8),
                getPeriodMock(90.0, begin.plusDays(15), 8),
                getPeriodMock(90.0, begin.plusDays(16), 8),
                getPeriodMock(90.0, begin.plusDays(17), 8),
                getPeriodMock(90.0, begin.plusDays(18), 8),
                getPeriodMock(90.0, begin.plusDays(19), 8, 1, 0),
                getPeriodMock(90.0, begin.plusDays(20), 8, 59, 59));

        final var workedHoursToCheck = WorkedHours.builder()
                .hours(161L)
                .minutes(0L)
                .seconds(59L)
                .build();

        when(periodRepository.findByDateRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Flux.fromIterable(periods));

        StepVerifier.create(taxesService.calculateWorkedHours(begin, LocalDateTime.now()))
                .assertNext(workedHours -> {
                    assertEquals(workedHoursToCheck.getHours(), workedHours.getHours());
                    assertEquals(workedHoursToCheck.getMinutes(), workedHours.getMinutes());
                    assertEquals(workedHoursToCheck.getSeconds(), workedHours.getSeconds());
                })
                .expectComplete()
                .verify();
    }
}