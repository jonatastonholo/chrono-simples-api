package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.domain.event.StopwatchStartEvent;
import dev.tonholo.chronosimplesapi.exception.ApiNotFoundException;
import dev.tonholo.chronosimplesapi.service.transformer.StopwatchTransformer;
import dev.tonholo.chronosimplesapi.validator.StopwatchStartValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.STOPWATCH_NOT_RUNNING;

@Service
@RequiredArgsConstructor
@Slf4j
public class StopwatchService {
    private final PeriodService periodService;
    private final StopwatchStartValidation stopwatchStartValidation;
    private final StopwatchTransformer stopwatchTransformer;

    public Mono<Period> start(StopwatchStartEvent stopwatchStartEvent) {
        log.info("Initializing stopwatch for -> {}", stopwatchStartEvent);
        return Mono.just(stopwatchStartEvent)
                .doOnNext(stopwatchStartValidation::validate)
                .map(stopwatchTransformer::from)
                .flatMap(periodService::create);
    }

    public Mono<Period> stop() {
        log.info("Stopping stopwatch");
        return periodService.findMostRecentPeriodWithoutEnd()
                .switchIfEmpty(Mono.error(new ApiNotFoundException(STOPWATCH_NOT_RUNNING)))
                .flatMap(period -> {
                    period.setEnd(LocalDateTime.now());
                    return periodService.save(period);
                });
    }
}
