package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.domain.event.PeriodCreationEvent;
import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.repository.postgres.PeriodRepository;
import dev.tonholo.chronosimplesapi.repository.postgres.ProjectRepository;
import dev.tonholo.chronosimplesapi.service.transformer.PeriodTransformer;
import dev.tonholo.chronosimplesapi.validator.PeriodCreationEventValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PERIOD_IS_CONCOMITANT;
import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PROJECT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeriodService {

    private final PeriodRepository periodRepository;
    private final ProjectRepository projectRepository;
    private final PeriodTransformer periodTransformer;
    private final PeriodCreationEventValidation periodCreationEventValidation;

    public Mono<Period> create(PeriodCreationEvent periodCreationEvent) {
        log.info("Creating new period -> {}", periodCreationEvent);
        return Mono.just(periodCreationEvent)
                .doOnNext(periodCreationEventValidation::validate)
                .flatMap(periodCreationEventValid -> projectRepository.findById(periodCreationEventValid.getProjectId()))
                .switchIfEmpty(Mono.error(new ApiException(PROJECT_NOT_FOUND)))
                .map(project -> {
                    log.info("Checking if the project provided exists");
                    final Period period = periodTransformer.from(periodCreationEvent);
                    if (Objects.isNull(period.getHourValue())) {
                        period.setHourValue(project.getHourValue());
                    }
                    if (Objects.isNull(period.getBegin())) {
                        period.setBegin(LocalDateTime.now());
                        log.info("Setting begin time to {}", period.getBegin());
                    }
                    return period;
                })
                .flatMap(periodToSave ->
                {
                    log.info("Checking if the period has concurrency");
                    return periodRepository.findAll()
                            .filter(periodSaved
                                    -> periodSaved.hasConcurrency(periodCreationEvent.getBegin(), periodCreationEvent.getEnd()))
                            .hasElements()
                            .map(hasConcurrency -> {
                                if (Boolean.TRUE.equals(hasConcurrency)) {
                                    throw new ApiException(PERIOD_IS_CONCOMITANT);
                                }
                                return periodToSave;
                            });
                })
                .flatMap(periodRepository::save);
    }

    public Flux<Period> findAll() {
        return periodRepository.findAll();
    }
}
