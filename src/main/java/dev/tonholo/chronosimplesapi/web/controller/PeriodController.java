package dev.tonholo.chronosimplesapi.web.controller;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.service.PeriodService;
import dev.tonholo.chronosimplesapi.web.model.PeriodCreationRequest;
import dev.tonholo.chronosimplesapi.web.transformer.PeriodWebTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.BODY_REQUIRED;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PeriodController {
    private final PeriodService periodService;
    private final PeriodWebTransformer periodWebTransformer;

    @NotNull
    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(PeriodCreationRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .map(periodWebTransformer::from)
                .flatMap(periodService::create)
                .map(periodWebTransformer::from)
                .flatMap(projectResponse ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(projectResponse));
    }
}
