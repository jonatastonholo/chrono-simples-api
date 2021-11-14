package dev.tonholo.chronosimplesapi.web.model;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

public interface Routes {
    RouterFunction<ServerResponse> routes();
}
