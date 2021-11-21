package dev.tonholo.chronosimplesapi.web.controller;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.service.PeriodService;
import dev.tonholo.chronosimplesapi.web.dto.PeriodRequest;
import dev.tonholo.chronosimplesapi.web.dto.PeriodResponse;
import dev.tonholo.chronosimplesapi.web.transformer.PeriodWebTransformer;
import lombok.RequiredArgsConstructor;
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
public class PeriodController {
    private final PeriodService periodService;
    private final PeriodWebTransformer periodWebTransformer;

    @NotNull
    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(PeriodRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .map(periodWebTransformer::from)
                .flatMap(periodService::create)
                .map(periodWebTransformer::from)
                .flatMap(periodResponse ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(periodResponse));
    }

    public Mono<ServerResponse> findAll() {
        final var periodFlux = periodService
                .findAll()
                .map(periodWebTransformer::from);

        return periodFlux
                .hasElements()
                .flatMap(hasElements ->
                        Boolean.TRUE.equals(hasElements)
                                ? ServerResponse
                                    .ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(periodFlux, PeriodResponse.class)
                                : ServerResponse
                                    .noContent()
                                    .build());
    }

    @NotNull
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        final var periodId = serverRequest.pathVariable("id");
        return serverRequest
                .bodyToMono(PeriodRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .map(periodRequest -> periodWebTransformer.from(periodRequest, periodId))
                .flatMap(periodService::update)
                .map(periodWebTransformer::from)
                .flatMap(periodResponse ->
                        ServerResponse
                                .ok()
                                .bodyValue(periodResponse));
    }

    @NotNull
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        final var periodId = serverRequest.pathVariable("id");
        return periodService.delete(periodId)
                .map(periodWebTransformer::from)
                .flatMap(periodResponse ->
                        ServerResponse.ok()
                                .bodyValue(periodResponse));
    }
}
