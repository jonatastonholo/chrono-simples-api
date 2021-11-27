package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.domain.Expense;
import dev.tonholo.chronosimplesapi.repository.postgres.mapper.ExpenseMapper;
import dev.tonholo.chronosimplesapi.repository.postgres.reactive.ExpenseReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseRepository {
    private final ExpenseReactiveRepository expenseReactiveRepository;
    private final ExpenseMapper expenseMapper;
    

    public Mono<Expense> save(Expense expense) {
        log.debug("Saving expense -> {}", expense);
        return Mono.just(expenseMapper.from(expense))
                .flatMap(expenseReactiveRepository::save)
                .map(expenseMapper::from);
    }

    public Mono<Expense> findById(String expenseId) {
        return expenseReactiveRepository
                .findByIdNotDeleted(expenseId)
                .map(expenseMapper::from);
    }

    public Flux<Expense> findAll() {
        return expenseReactiveRepository
                .findAllNotDeleted()
                .map(expenseMapper::from);
    }

    public Mono<Expense> delete(Expense expense) {
        return Mono.just(expense)
                .map(expenseMapper::from)
                .map(expenseEntity -> {
                    log.debug("Soft Deleting expense -> {}", expense);
                    expenseEntity.setDeleted(true);
                    expenseEntity.setUpdatedAt(LocalDateTime.now());
                    return expenseEntity;
                })
                .flatMap(expenseReactiveRepository::save)
                .map(expenseMapper::from);
    }
}
