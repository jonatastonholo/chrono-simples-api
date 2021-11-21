package dev.tonholo.chronosimplesapi.web.router;

import dev.tonholo.chronosimplesapi.web.controller.StopwatchController;
import dev.tonholo.chronosimplesapi.web.domain.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@RequiredArgsConstructor
public class StopwatchRouter  implements Routes {

    private final StopwatchController stopwatchController;

    @Override
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route()
                .PATCH("/start", accept(APPLICATION_JSON), stopwatchController::start)
                .PATCH("/stop", accept(APPLICATION_JSON), stopwatchController::stop)
                .GET("/listen", accept(APPLICATION_JSON), request -> stopwatchController.listen())
                .build();
    }
}
