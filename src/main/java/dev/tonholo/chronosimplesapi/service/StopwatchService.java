package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.exception.ApiNotFoundException;
import dev.tonholo.chronosimplesapi.service.event.StopwatchResultEvent;
import dev.tonholo.chronosimplesapi.service.event.StopwatchStartEvent;
import dev.tonholo.chronosimplesapi.service.transformer.StopwatchTransformer;
import dev.tonholo.chronosimplesapi.service.validation.StopwatchStartValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.STOPWATCH_NOT_RUNNING_TO_LISTEN;
import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.STOPWATCH_NOT_RUNNING_TO_STOP;

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
                .switchIfEmpty(Mono.error(new ApiNotFoundException(STOPWATCH_NOT_RUNNING_TO_STOP)))
                .flatMap(period -> {
                    period.setEnd(LocalDateTime.now());
                    return periodService.save(period);
                });
    }

    public Flux<StopwatchResultEvent> listen() {
        return periodService.findMostRecentPeriodWithoutEnd()
                .switchIfEmpty(Mono.error(new ApiNotFoundException(STOPWATCH_NOT_RUNNING_TO_LISTEN)))
                .flatMapMany(period -> {
                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
                    Flux<StopwatchResultEvent> events
                            = Flux.fromStream(Stream.generate(()
                                -> calculateTimeElapsedInSeconds(period)));
                    return Flux.zip(events, interval, (key, value) -> key);
                });
    }

    private StopwatchResultEvent calculateTimeElapsedInSeconds(Period period) {
        final var begin = period.getBegin();
        final var now = LocalDateTime.now();
        final var timeElapsedInMillis = Duration.between(begin, now).toMillis();
        return StopwatchResultEvent.builder()
                .projectId(period.getProjectId())
                .stopwatchBegin(begin)
                .days(TimeUnit.MILLISECONDS.toDays(timeElapsedInMillis))
                .hours(TimeUnit.MILLISECONDS.toHours(timeElapsedInMillis) % 24)
                .minutes(TimeUnit.MILLISECONDS.toMinutes(timeElapsedInMillis) % 60)
                .seconds(TimeUnit.MILLISECONDS.toSeconds(timeElapsedInMillis)  % 60)
                .millis(timeElapsedInMillis)
                .build();
    }
}
