package dev.tonholo.chronosimplesapi.web.router;

import dev.tonholo.chronosimplesapi.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class Router {
    private static final List<String> PUBLIC_ENDPOINTS = List.of("/health");

    private final ProjectRouter projectRouter;
    private final SecurityService securityService;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions
                .route()
                .before(this::handleLogger)
                .before(this::handleSecurity)
                .after(this::handleResponse)

                .GET("/health", request -> ServerResponse.ok().build())
                .path("/chrono-simples/v1/projects", projectRouter::routes)
                .build();
    }

    private ServerRequest handleLogger(ServerRequest serverRequest) {
        log.info("Initializing request process. {} -> {}", serverRequest.method(), serverRequest.uri().getPath());
        return serverRequest;
    }

    private ServerRequest handleSecurity(ServerRequest serverRequest) {
        if (!PUBLIC_ENDPOINTS.contains(serverRequest.uri().getPath())) {
            securityService.authorize(serverRequest.headers());
        }
        return serverRequest;
    }

    private ServerResponse handleResponse(ServerRequest serverRequest, ServerResponse serverResponse) {
        if (serverResponse.statusCode().is1xxInformational()
                ||  serverResponse.statusCode().is2xxSuccessful()
                || serverResponse.statusCode().is3xxRedirection()) {
            log.info("Finishing successful processing (status {}) of request {} -> {}",
                    serverResponse.statusCode().value(),
                    serverRequest.method(),
                    serverRequest.uri().getPath());
        } else if (serverResponse.statusCode().is4xxClientError()) {
            log.warn("Finishing processing with expected errors (status {}) of request {} -> {}",
                    serverResponse.statusCode().value(),
                    serverRequest.method(),
                    serverRequest.uri().getPath());
        } else if (serverResponse.statusCode().is5xxServerError()) {
            log.error("Unexpected error during request processing (status {}) of request {} -> {}",
                    serverResponse.statusCode().value(),
                    serverRequest.method(),
                    serverRequest.uri().getPath());
        }
        return serverResponse;
    }
}