package dev.tonholo.chronosimplesapi.repository.postgres.reactive;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.PeriodEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PeriodReactiveRepository extends ReactiveCrudRepository<PeriodEntity, String> {

    @Query("SELECT * FROM db_chrono_simples.tb_period WHERE deleted IS FALSE ORDER BY period_begin DESC")
    Flux<PeriodEntity> findAllNotDeleted();

    @Query("SELECT * FROM db_chrono_simples.tb_period WHERE id = $1 AND deleted IS FALSE")
    Mono<PeriodEntity> findByIdNotDeleted(String periodId);

    @Query("SELECT * FROM db_chrono_simples.tb_period WHERE period_end IS NULL AND deleted IS FALSE ORDER BY period_begin DESC LIMIT 1")
    Mono<PeriodEntity> findMostRecentPeriodWithoutEnd();


    @Query("SELECT * FROM db_chrono_simples.tb_period WHERE period_begin >= $1 AND period_end <= $2 AND deleted IS FALSE")
    Flux<PeriodEntity> findByDateRange(LocalDateTime begin, LocalDateTime end);
}
