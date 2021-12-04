package dev.tonholo.chronosimplesapi.web.controller;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.service.SessionService;
import dev.tonholo.chronosimplesapi.web.dto.SessionCreationRequest;
import dev.tonholo.chronosimplesapi.web.dto.SessionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.BODY_REQUIRED;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SessionController {
    private final SessionService sessionService;
    private SessionResponse sessionResponse;

    @NotNull
    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(SessionCreationRequest.class)
                .switchIfEmpty(Mono.error(new ApiException(BODY_REQUIRED)))
                .map(sessionCreationRequest -> {

                    if (!sessionCreationRequest.getEmail().equals("jonatas@tonholo.dev")
                        || !sessionCreationRequest.getPassword().equals("123456")) {
                        sessionResponse = null;
                        throw new UnsupportedOperationException();
                    }
                    log.info("Creating session for {}", sessionCreationRequest.getEmail());
                    sessionResponse = SessionResponse.builder()
                            .email("jonatas@tonholo.dev")
                            .name("Jonatas")
                            .build();
                    return sessionResponse;
                })
                .flatMap(sessionResponse -> ServerResponse.ok()
                        .bodyValue(sessionResponse));
    }

    @NotNull
    public Mono<ServerResponse> getSession(ServerRequest serverRequest) {
        if (sessionResponse == null) {
            return ServerResponse.noContent()
                    .build();
        }
        return ServerResponse.ok()
                .bodyValue(sessionResponse);
    }

    @NotNull
    public Mono<ServerResponse> close(ServerRequest serverRequest) {
        sessionResponse = null;
        return ServerResponse.ok()
                .build();
    }
}
