package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.Expense;
import dev.tonholo.chronosimplesapi.domain.event.ExpenseCreationEvent;
import dev.tonholo.chronosimplesapi.domain.event.ExpenseUpdateEvent;
import dev.tonholo.chronosimplesapi.exception.ApiNotFoundException;
import dev.tonholo.chronosimplesapi.repository.postgres.ExpenseRepository;
import dev.tonholo.chronosimplesapi.service.transformer.ExpenseTransformer;
import dev.tonholo.chronosimplesapi.validator.ExpenseCreationEventValidation;
import dev.tonholo.chronosimplesapi.validator.ExpenseUpdateEventValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.EXPENSE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseCreationEventValidation expenseCreationEventValidation;
    private final ExpenseUpdateEventValidation expenseUpdateEventValidation;
    private final ExpenseRepository expenseRepository;
    private final ExpenseTransformer expenseTransformer;

    public Mono<Expense> create(ExpenseCreationEvent expenseCreationEvent) {
        log.info("Creating new expense -> {}", expenseCreationEvent);
        return Mono.just(expenseCreationEvent)
                .doOnNext(expenseCreationEventValidation::validate)
                .map(expenseTransformer::from)
                .flatMap(expenseRepository::save);
    }

    public Flux<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public Mono<Expense> findById(String expenseId) {
        return expenseRepository.findById(expenseId)
                .switchIfEmpty(Mono.error(new ApiNotFoundException(EXPENSE_NOT_FOUND)));
    }

    public Mono<Expense> update(ExpenseUpdateEvent expenseUpdateEvent) {
        return Mono.just(expenseUpdateEvent)
                .doOnNext(expenseUpdateEventValidation::validate)
                .flatMap(expenseUpdateEventValid ->
                        expenseRepository.findById(expenseUpdateEventValid.getId()))
                .switchIfEmpty(Mono.error(new ApiNotFoundException(EXPENSE_NOT_FOUND)))
                .flatMap(expenseSaved -> {
                    log.info("Updating expense -> {}", expenseUpdateEvent);
                    final Expense expenseToUpdate = expenseTransformer.from(expenseUpdateEvent);
                    return Mono.just(expenseToUpdate.completeFrom(expenseSaved));
                })
                .flatMap(expenseRepository::save);
    }

    public Mono<Expense> delete(String expenseId) {
        return expenseRepository.findById(expenseId)
                .switchIfEmpty(Mono.error(new ApiNotFoundException(EXPENSE_NOT_FOUND)))
                .flatMap(expenseRepository::delete);
    }
}
