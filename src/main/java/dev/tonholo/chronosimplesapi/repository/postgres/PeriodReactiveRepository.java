package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.PeriodEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

interface PeriodReactiveRepository extends ReactiveCrudRepository<PeriodEntity, String> {

    @Query("SELECT COUNT(*) FROM db_chrono_simples.tb_period WHERE period_end IS NULL OR $1 BETWEEN period_begin AND period_end")
    Mono<Long> hasConcurrency(LocalDateTime timerBegin);
}
