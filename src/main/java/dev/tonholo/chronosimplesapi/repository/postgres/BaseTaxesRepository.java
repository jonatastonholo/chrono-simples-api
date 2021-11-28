package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.config.AppConfig;
import dev.tonholo.chronosimplesapi.domain.BaseTaxes;
import dev.tonholo.chronosimplesapi.exception.ApiInternalException;
import dev.tonholo.chronosimplesapi.repository.postgres.mapper.TaxMapper;
import dev.tonholo.chronosimplesapi.repository.postgres.reactive.DasBaseEntityReactiveRepository;
import dev.tonholo.chronosimplesapi.repository.postgres.reactive.InssBaseEntityReactiveRepository;
import dev.tonholo.chronosimplesapi.repository.postgres.reactive.IrrfBaseEntityReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class BaseTaxesRepository {
    private final AppConfig appConfig;
    private final IrrfBaseEntityReactiveRepository irrfBaseEntityReactiveRepository;
    private final DasBaseEntityReactiveRepository dasBaseEntityReactiveRepository;
    private final InssBaseEntityReactiveRepository inssBaseEntityReactiveRepository;
    private final TaxMapper taxMapper;

    @Bean
    @Primary
    public BaseTaxes load() {
        log.info("Loading IRRF Bases");
        final var irrfBases = irrfBaseEntityReactiveRepository
                .findAll()
                .map(taxMapper::from)
                .collectList()
                .block();

        log.info("Loading DAS Bases");
        final var dasBases = dasBaseEntityReactiveRepository
                .findAll()
                .map(taxMapper::from)
                .collectList()
                .block();

        log.info("Loading INSS Bases");
        final var inssBases = inssBaseEntityReactiveRepository
                .findAll()
                .map(taxMapper::from)
                .collectList()
                .block();

        log.info("Loading Deduction Amount Per Financial Dependent");
        final var deductionAmountPerFinancialDependent = appConfig.getDeductionAmountPerFinancialDependent();

        log.info("Loading Default R Factor");
        final var defaultRFactor = appConfig.getDefaultRFactor();

        log.info("Loading Pension Ceiling value");
        final var pensionCeiling = appConfig.getPensionCeiling();

        log.info("Loading Base Taxes");
        final BaseTaxes baseTaxes = BaseTaxes.builder()
                .irrfBases(irrfBases)
                .dasBases(dasBases)
                .inssBases(inssBases)
                .pensionCeiling(pensionCeiling)
                .deductionAmountPerFinancialDependent(deductionAmountPerFinancialDependent)
                .defaultRFactor(defaultRFactor)
                .build();
        validate(baseTaxes);

        log.info("Base Taxes successfully loaded");
        return baseTaxes;
    }

    private void validate(BaseTaxes baseTaxes) {
        log.info("Validating loaded base taxes");
        final var inssBases = baseTaxes.getInssBases();
        final var irrfBases = baseTaxes.getIrrfBases();
        final var dasBases = baseTaxes.getDasBases();
        final var deductionAmountPerFinancialDependent = baseTaxes.getDeductionAmountPerFinancialDependent();
        final var defaultRFactor = baseTaxes.getDefaultRFactor();
        final var pensionCeiling = baseTaxes.getPensionCeiling();

        if (CollectionUtils.isEmpty(inssBases)
                || CollectionUtils.isEmpty(irrfBases)
                || CollectionUtils.isEmpty(dasBases)
                || Objects.isNull(deductionAmountPerFinancialDependent)
                || Objects.isNull(defaultRFactor)
                || Objects.isNull(pensionCeiling)
        ){

            throw new ApiInternalException("Failed to load Base Taxes -> {}", baseTaxes);
        }
    }
}
