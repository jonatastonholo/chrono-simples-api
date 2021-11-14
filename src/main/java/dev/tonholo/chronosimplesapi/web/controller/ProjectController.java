package dev.tonholo.chronosimplesapi.web.controller;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.service.ProjectService;
import dev.tonholo.chronosimplesapi.validator.ProjectRequestValidation;
import dev.tonholo.chronosimplesapi.web.model.ProjectRequest;
import dev.tonholo.chronosimplesapi.web.model.ProjectResponse;
import dev.tonholo.chronosimplesapi.web.transformer.ProjectWebTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.BODY_REQUIRED;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
    private final ProjectRequestValidation projectRequestValidation;
    private final ProjectWebTransformer projectWebTransformer;
    private final ProjectService projectService;

    @NotNull
    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(ProjectRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .doOnNext(projectRequestValidation::validate)
                .map(projectWebTransformer::from)
                .flatMap(projectService::create)
                .map(projectWebTransformer::from)
                .flatMap(projectResponse ->
                    ServerResponse
                            .status(HttpStatus.CREATED)
                            .bodyValue(projectResponse));
    }

    public Mono<ServerResponse> findAll() {
        final var projectFlux = projectService
                .findAll()
                .map(projectWebTransformer::from);

        return projectFlux
                .hasElements()
                .flatMap(hasElements ->
                        Boolean.TRUE.equals(hasElements)
                                ? ServerResponse
                                    .ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(projectFlux, ProjectResponse.class)
                                : ServerResponse
                                    .noContent()
                                    .build());
    }

    @NotNull
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        final var projectId = serverRequest.pathVariable("id");
        return serverRequest
                .bodyToMono(ProjectRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .map(projectRequest -> projectWebTransformer.from(projectRequest, projectId))
                .flatMap(projectService::update)
                .map(projectWebTransformer::from)
                .flatMap(projectResponse ->
                        ServerResponse
                                .ok()
                                .bodyValue(projectResponse));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        final var projectId = serverRequest.pathVariable("id");
        return projectService.delete(projectId)
                .map(projectWebTransformer::from)
                .flatMap(projectResponse ->
                        ServerResponse.ok()
                                .bodyValue(projectResponse));

    }
}
