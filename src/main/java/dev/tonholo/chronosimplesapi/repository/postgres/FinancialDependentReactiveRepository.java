package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.FinancialDependentEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface FinancialDependentReactiveRepository extends ReactiveCrudRepository<FinancialDependentEntity, String> {

    @Query("SELECT * FROM db_chrono_simples.tb_financial_dependent WHERE deleted IS FALSE ORDER BY period_begin DESC")
    Flux<FinancialDependentEntity> findAllNotDeleted();

    @Query("SELECT * FROM db_chrono_simples.tb_financial_dependent WHERE id = $1 AND deleted IS FALSE")
    Mono<FinancialDependentEntity> findByIdNotDeleted(String financialDependentId);
}
