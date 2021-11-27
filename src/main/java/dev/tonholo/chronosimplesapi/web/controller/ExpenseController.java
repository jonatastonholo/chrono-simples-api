package dev.tonholo.chronosimplesapi.web.controller;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.service.ExpenseService;
import dev.tonholo.chronosimplesapi.web.converter.ExpenseConverter;
import dev.tonholo.chronosimplesapi.web.dto.ExpenseRequest;
import dev.tonholo.chronosimplesapi.web.dto.ExpenseResponse;
import dev.tonholo.chronosimplesapi.web.validation.ExpenseRequestValidation;
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
public class ExpenseController {
    private final ExpenseRequestValidation expenseRequestValidation;
    private final ExpenseConverter expenseConverter;
    private final ExpenseService expenseService;

    @NotNull
    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(ExpenseRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .doOnNext(expenseRequestValidation::validate)
                .map(expenseConverter::from)
                .flatMap(expenseService::create)
                .map(expenseConverter::from)
                .flatMap(expenseResponse ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(expenseResponse));
    }

    public Mono<ServerResponse> findAll() {
        final var expenseFlux = expenseService
                .findAll()
                .map(expenseConverter::from);

        return expenseFlux
                .hasElements()
                .flatMap(hasElements ->
                        Boolean.TRUE.equals(hasElements)
                                ? ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(expenseFlux, ExpenseResponse.class)
                                : ServerResponse
                                .noContent()
                                .build());
    }

    @NotNull
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        final var expenseId = serverRequest.pathVariable("id");
        return serverRequest
                .bodyToMono(ExpenseRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .map(expenseRequest -> expenseConverter.from(expenseRequest, expenseId))
                .flatMap(expenseService::update)
                .map(expenseConverter::from)
                .flatMap(expenseResponse ->
                        ServerResponse
                                .ok()
                                .bodyValue(expenseResponse));
    }

    @NotNull
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        final var expenseId = serverRequest.pathVariable("id");
        return expenseService.delete(expenseId)
                .map(expenseConverter::from)
                .flatMap(expenseResponse ->
                        ServerResponse.ok()
                                .bodyValue(expenseResponse));

    }
}
