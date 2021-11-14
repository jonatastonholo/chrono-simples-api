package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.domain.event.ProjectCreationEvent;
import dev.tonholo.chronosimplesapi.domain.event.ProjectUpdateEvent;
import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.exception.ApiNotFoundException;
import dev.tonholo.chronosimplesapi.repository.postgres.ProjectRepository;
import dev.tonholo.chronosimplesapi.service.transformer.ProjectTransformer;
import dev.tonholo.chronosimplesapi.validator.ProjectCreationEventValidation;
import dev.tonholo.chronosimplesapi.validator.ProjectUpdateEventValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PROJECT_ALREADY_EXISTS;
import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PROJECT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectCreationEventValidation projectCreationEventValidation;
    private final ProjectUpdateEventValidation projectUpdateEventValidation;
    private final ProjectRepository projectRepository;
    private final ProjectTransformer projectTransformer;

    public Mono<Project> create(ProjectCreationEvent projectCreationEvent) {
        log.info("Creating new project -> {}", projectCreationEvent);
        return Mono.just(projectCreationEvent)
                .doOnNext(projectCreationEventValidation::validate)
                .flatMap(projectCreationEventValid ->
                    projectRepository.findByName(projectCreationEventValid.getName())
                            .hasElement()
                            .flatMap(hasElement -> {
                                if (Boolean.TRUE.equals(hasElement)) {
                                    return Mono.error(new ApiException(PROJECT_ALREADY_EXISTS));
                                }
                                return Mono.just(projectCreationEventValid);
                            }))
                .map(projectTransformer::from)
                .flatMap(projectRepository::save);
    }


    public Flux<Project> findAll() {
        return projectRepository.listAll();
    }

    public Mono<Project> update(ProjectUpdateEvent projectUpdateEvent) {
        return Mono.just(projectUpdateEvent)
                .doOnNext(projectUpdateEventValidation::validate)
                .flatMap(projectUpdateEventValid ->
                        projectRepository.findById(projectUpdateEventValid.getId()))
                .switchIfEmpty(Mono.error(new ApiNotFoundException(PROJECT_NOT_FOUND)))
                .flatMap(projectSaved ->
                        projectRepository.findByName(projectUpdateEvent.getName())
                                .switchIfEmpty(Mono.just(projectSaved))
                                .flatMap(projectSavedWithSameName -> {
                                    if (!projectSaved.getId().equals(projectSavedWithSameName.getId())) {
                                        return Mono.error(new ApiException(PROJECT_ALREADY_EXISTS));
                                    }
                                    log.info("Updating project -> {}", projectUpdateEvent);
                                    final Project projectToUpdate = projectTransformer.from(projectUpdateEvent);
                                    return Mono.just(projectToUpdate.merge(projectSaved));
                                }))
                .flatMap(projectRepository::save);
    }
}
