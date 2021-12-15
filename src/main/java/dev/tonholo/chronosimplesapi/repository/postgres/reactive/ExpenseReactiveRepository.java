package dev.tonholo.chronosimplesapi.repository.postgres.reactive;

import dev.tonholo.chronosimplesapi.domain.Expense;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.ExpenseEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface ExpenseReactiveRepository extends ReactiveCrudRepository<ExpenseEntity, String> {

    @Query("SELECT * FROM db_chrono_simples.tb_expense WHERE deleted IS FALSE ORDER BY period_begin DESC")
    Flux<ExpenseEntity> findAllNotDeleted();

    @Query("SELECT * FROM db_chrono_simples.tb_expense WHERE id = $1 AND deleted IS FALSE")
    Mono<ExpenseEntity> findByIdNotDeleted(String expenseId);

    @Query("SELECT * FROM db_chrono_simples.tb_expense WHERE deleted IS FALSE AND period_begin >= $1 AND period_end <= $2")
    Flux<Expense> findByDateRange(LocalDateTime periodBegin, LocalDateTime periodEnd);
}
