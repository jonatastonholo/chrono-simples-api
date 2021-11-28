package dev.tonholo.chronosimplesapi.repository.postgres.reactive;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.FinancialDependentEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface FinancialDependentReactiveRepository extends ReactiveCrudRepository<FinancialDependentEntity, String> {

    @Query("SELECT * FROM db_chrono_simples.tb_financial_dependent WHERE deleted IS FALSE ORDER BY period_begin DESC")
    Flux<FinancialDependentEntity> findAllNotDeleted();

    @Query("SELECT * FROM db_chrono_simples.tb_financial_dependent WHERE id = $1 AND deleted IS FALSE")
    Mono<FinancialDependentEntity> findByIdNotDeleted(String financialDependentId);

    @Query("SELECT COUNT(*) FROM db_chrono_simples.tb_financial_dependent WHERE period_begin >= $1 AND period_end <= $2 AND deleted IS FALSE")
    Mono<Integer> countByPeriodRange(LocalDate periodBegin, LocalDate periodEnd);

    @Query("SELECT * FROM db_chrono_simples.tb_financial_dependent WHERE deleted IS FALSE AND period_begin >= $1 AND period_end <= $2")
    Flux<FinancialDependentEntity> findByPeriodRange(LocalDate periodBegin, LocalDate periodEnd);
}
