package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.PeriodEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface PeriodReactiveRepository extends ReactiveCrudRepository<PeriodEntity, String> {

    @Query("SELECT * FROM db_chrono_simples.tb_period WHERE deleted IS FALSE ORDER BY period_begin DESC")
    Flux<PeriodEntity> findAllNotDeleted();

    @Query("SELECT * FROM db_chrono_simples.tb_period WHERE id = $1 AND deleted IS FALSE")
    Mono<PeriodEntity> findByIdNotDeleted(String periodId);

    @Query("SELECT * FROM db_chrono_simples.tb_period WHERE period_end IS NULL AND deleted IS FALSE ORDER BY period_begin DESC LIMIT 1")
    Mono<PeriodEntity> findMostRecentPeriodWithoutEnd();

}
