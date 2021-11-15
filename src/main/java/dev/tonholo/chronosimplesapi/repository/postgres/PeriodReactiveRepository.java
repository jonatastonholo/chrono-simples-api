package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.PeriodEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

interface PeriodReactiveRepository extends ReactiveCrudRepository<PeriodEntity, String> {

    @Query("SELECT * FROM db_chrono_simples.tb_period WHERE deleted IS FALSE ORDER BY period_begin DESC")
    Flux<PeriodEntity> findAllNotDeleted();

}
