package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.ExpenseEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface ExpenseReactiveRepository extends ReactiveCrudRepository<ExpenseEntity, String> {

    @Query("SELECT * FROM db_chrono_simples.tb_expense WHERE deleted IS FALSE ORDER BY period_begin DESC")
    Flux<ExpenseEntity> findAllNotDeleted();

    @Query("SELECT * FROM db_chrono_simples.tb_expense WHERE id = $1 AND deleted IS FALSE")
    Mono<ExpenseEntity> findByIdNotDeleted(String expenseId);
}
