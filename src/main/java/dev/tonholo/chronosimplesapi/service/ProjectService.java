package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.domain.event.ProjectCreationEvent;
import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.repository.postgres.ProjectRepository;
import dev.tonholo.chronosimplesapi.service.transformer.ProjectTransformer;
import dev.tonholo.chronosimplesapi.validator.ProjectCreationEventValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PROJECT_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectCreationEventValidation projectCreationEventValidation;
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
}
