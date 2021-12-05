package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.exception.ApiNotFoundException;
import dev.tonholo.chronosimplesapi.repository.postgres.PeriodRepository;
import dev.tonholo.chronosimplesapi.repository.postgres.ProjectRepository;
import dev.tonholo.chronosimplesapi.service.event.PeriodCreationEvent;
import dev.tonholo.chronosimplesapi.service.event.PeriodUpdateEvent;
import dev.tonholo.chronosimplesapi.service.transformer.PeriodTransformer;
import dev.tonholo.chronosimplesapi.service.validation.PeriodCreationEventValidation;
import dev.tonholo.chronosimplesapi.service.validation.PeriodUpdateEventValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeriodService {

    private final PeriodRepository periodRepository;
    private final ProjectRepository projectRepository;
    private final PeriodTransformer periodTransformer;

    private final PeriodCreationEventValidation periodCreationEventValidation;
    private final PeriodUpdateEventValidation periodUpdateEventValidation;

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
                    if (Objects.isNull(period.getCurrency())) {
                        period.setCurrency(project.getCurrencyCode());
                    }
                    if (Objects.isNull(period.getBegin())) {
                        period.setBegin(LocalDateTime.now());
                        log.info("Setting begin time to {}", period.getBegin());
                    }
                    return period;
                })
                .flatMap(this::checkIfPeriodHaveConcurrency)
                .flatMap(periodRepository::save)
                .flatMap(periodSaved ->
                        projectRepository.findById(periodSaved.getProjectId())
                                .map(project -> {
                                    periodSaved.setProject(project);
                                    return periodSaved;
                                })
                );
    }

    public Flux<Period> findAll() {
        return periodRepository.findAll();
    }

    public Mono<Period> findMostRecentPeriodWithoutEnd() {
        return periodRepository.findMostRecentPeriodWithoutEnd();
    }

    public Mono<Period> update(PeriodUpdateEvent periodUpdateEvent) {
        log.info("Updating period -> {}", periodUpdateEvent);
        return Mono.just(periodUpdateEvent)
                .doOnNext(periodUpdateEventValidation::validate)
                .flatMap(unused ->
                        periodRepository.findById(periodUpdateEvent.getId()))
                .switchIfEmpty(Mono.error(new ApiNotFoundException(PERIOD_NOT_FOUND)))
                .flatMap(periodSaved ->
                        projectRepository
                                .findById(Strings.isNotBlank(periodUpdateEvent.getProjectId())
                                        ? periodUpdateEvent.getProjectId()
                                        : periodSaved.getProjectId())
                                .switchIfEmpty(Mono.error(new ApiException(PROJECT_NOT_FOUND)))
                                .flatMap(projectSaved -> {
                                    log.info("Updating period -> {}", periodUpdateEvent);
                                    final Period periodToUpdate = periodTransformer.from(periodUpdateEvent);
                                    return Mono.just(periodToUpdate.completeFrom(periodSaved));
                                }))
                .flatMap(this::checkIfPeriodHaveConcurrency)
                .flatMap(periodRepository::save)
                .flatMap(periodSaved ->
                        projectRepository.findById(periodSaved.getProjectId())
                                .map(project -> {
                                    periodSaved.setProject(project);
                                    return periodSaved;
                                })
                        );
    }

    @NotNull
    private Mono<Period> checkIfPeriodHaveConcurrency(Period toVerify) {
        log.info("Checking if the period have concurrency. Begin: {} | End: {}", toVerify.getBegin(), toVerify.getEnd());
        return periodRepository
                .hasConcurrency(toVerify)
                .map(hasConcurrency -> {
                    if (Boolean.TRUE.equals(hasConcurrency)) {
                        throw new ApiException(PERIOD_IS_CONCOMITANT);
                    }
                    return toVerify;
                });
    }

    public Mono<Boolean> delete(String periodId) {
        return periodRepository.findById(periodId)
                .switchIfEmpty(Mono.error(new ApiNotFoundException(PERIOD_NOT_FOUND)))
                .flatMap(periodRepository::delete)
                .map(unused -> true);
    }

    public Mono<Period> save(Period period) {
        return periodRepository.save(period);
    }
}
