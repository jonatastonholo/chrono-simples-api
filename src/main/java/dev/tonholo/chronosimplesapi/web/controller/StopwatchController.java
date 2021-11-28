package dev.tonholo.chronosimplesapi.web.controller;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.service.StopwatchService;
import dev.tonholo.chronosimplesapi.service.event.StopwatchResultEvent;
import dev.tonholo.chronosimplesapi.web.converter.StopwatchConverter;
import dev.tonholo.chronosimplesapi.web.dto.StopwatchRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.BODY_REQUIRED;

@Configuration
@RequiredArgsConstructor
public class StopwatchController {
    private final StopwatchService stopwatchService;
    private final StopwatchConverter stopwatchConverter;

    @NotNull
    public Mono<ServerResponse> start(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(StopwatchRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .map(stopwatchConverter::from)
                .flatMap(stopwatchService::start)
                .map(stopwatchConverter::from)
                .flatMap(periodResponse ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(periodResponse));
    }

    @NotNull
    public Mono<ServerResponse> stop(ServerRequest serverRequest) {
        return stopwatchService.stop()
                .map(stopwatchConverter::from)
                .flatMap(stopwatchResponse ->
                        ServerResponse.ok()
                                .bodyValue(stopwatchResponse));
    }

    public Mono<ServerResponse> listen() {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(stopwatchService.listen(), StopwatchResultEvent.class);
    }
}
