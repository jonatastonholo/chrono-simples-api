package dev.tonholo.chronosimplesapi.web.router;

import dev.tonholo.chronosimplesapi.web.controller.PeriodController;
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
public class PeriodRouter implements Routes {

    private final PeriodController periodController;

    @Override
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route()
                .POST("", accept(APPLICATION_JSON), periodController::create)
                .GET("", accept(APPLICATION_JSON), request -> periodController.findAll())
                .PUT("/{id}", accept(APPLICATION_JSON), periodController::update)
                .DELETE("/{id}", accept(APPLICATION_JSON), periodController::delete)
                .build();
    }
}
