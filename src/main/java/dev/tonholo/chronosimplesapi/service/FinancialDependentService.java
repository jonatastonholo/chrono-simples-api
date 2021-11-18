package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.FinancialDependent;
import dev.tonholo.chronosimplesapi.domain.event.FinancialDependentCreationEvent;
import dev.tonholo.chronosimplesapi.domain.event.FinancialDependentUpdateEvent;
import dev.tonholo.chronosimplesapi.exception.ApiNotFoundException;
import dev.tonholo.chronosimplesapi.repository.postgres.FinancialDependentRepository;
import dev.tonholo.chronosimplesapi.service.transformer.FinancialDependentTransformer;
import dev.tonholo.chronosimplesapi.validator.FinancialDependentCreationEventValidation;
import dev.tonholo.chronosimplesapi.validator.FinancialDependentUpdateEventValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.FINANCIAL_DEPENDENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinancialDependentService {

    private final FinancialDependentCreationEventValidation financialDependentCreationEventValidation;
    private final FinancialDependentUpdateEventValidation financialDependentUpdateEventValidation;
    private final FinancialDependentRepository financialDependentRepository;
    private final FinancialDependentTransformer financialDependentTransformer;

    public Mono<FinancialDependent> create(FinancialDependentCreationEvent financialDependentCreationEvent) {
        log.info("Creating new financial dependent -> {}", financialDependentCreationEvent);
        return Mono.just(financialDependentCreationEvent)
                .doOnNext(financialDependentCreationEventValidation::validate)
                .map(financialDependentTransformer::from)
                .flatMap(financialDependentRepository::save);
    }

    public Flux<FinancialDependent> findAll() {
        return financialDependentRepository.findAll();
    }

    public Mono<FinancialDependent> findById(String financialDependentId) {
        return financialDependentRepository.findById(financialDependentId)
                .switchIfEmpty(Mono.error(new ApiNotFoundException(FINANCIAL_DEPENDENT_NOT_FOUND)));
    }

    public Mono<FinancialDependent> update(FinancialDependentUpdateEvent financialDependentUpdateEvent) {
        return Mono.just(financialDependentUpdateEvent)
                .doOnNext(financialDependentUpdateEventValidation::validate)
                .flatMap(financialDependentUpdateEventValid ->
                        financialDependentRepository.findById(financialDependentUpdateEventValid.getId()))
                .switchIfEmpty(Mono.error(new ApiNotFoundException(FINANCIAL_DEPENDENT_NOT_FOUND)))
                .flatMap(financialDependentSaved -> {
                    log.info("Updating financial dependent -> {}", financialDependentUpdateEvent);
                    final FinancialDependent financialDependentToUpdate = financialDependentTransformer.from(financialDependentUpdateEvent);
                    return Mono.just(financialDependentToUpdate.completeFrom(financialDependentSaved));
                })
                .flatMap(financialDependentRepository::save);
    }

    public Mono<FinancialDependent> delete(String financialDependentId) {
        return financialDependentRepository.findById(financialDependentId)
                .switchIfEmpty(Mono.error(new ApiNotFoundException(FINANCIAL_DEPENDENT_NOT_FOUND)))
                .flatMap(financialDependentRepository::delete);
    }
}
