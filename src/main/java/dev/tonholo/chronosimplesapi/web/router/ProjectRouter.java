package dev.tonholo.chronosimplesapi.web.router;

import dev.tonholo.chronosimplesapi.web.controller.ProjectController;
import dev.tonholo.chronosimplesapi.web.model.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@RequiredArgsConstructor
public class ProjectRouter implements Routes {

    private final ProjectController projectController;

    @Override
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route()
                .POST("", accept(APPLICATION_JSON), projectController::create)
                .GET("", accept(APPLICATION_JSON), request -> projectController.findAll())
                .PUT("/{id}", accept(APPLICATION_JSON), projectController::update)
                .DELETE("/{id}", accept(APPLICATION_JSON), projectController::delete)
                .build();
    }
}
