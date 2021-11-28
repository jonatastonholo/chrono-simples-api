package dev.tonholo.chronosimplesapi.web.router;

import dev.tonholo.chronosimplesapi.web.controller.ExpenseController;
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
public class ExpenseRouter implements Routes {

    private final ExpenseController expenseController;

    @Override
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route()
                .POST("", accept(APPLICATION_JSON), expenseController::create)
                .GET("", accept(APPLICATION_JSON), request -> expenseController.findAll())
                .PUT("/{id}", accept(APPLICATION_JSON), expenseController::update)
                .DELETE("/{id}", accept(APPLICATION_JSON), expenseController::delete)
                .build();
    }
}
