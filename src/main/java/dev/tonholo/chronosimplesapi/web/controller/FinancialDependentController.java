package dev.tonholo.chronosimplesapi.web.controller;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.service.FinancialDependentService;
import dev.tonholo.chronosimplesapi.web.dto.FinancialDependentRequest;
import dev.tonholo.chronosimplesapi.web.dto.FinancialDependentResponse;
import dev.tonholo.chronosimplesapi.web.transformer.FinancialDependentWebTransformer;
import dev.tonholo.chronosimplesapi.web.validation.FinancialDependentRequestValidation;
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
public class FinancialDependentController {
    private final FinancialDependentRequestValidation financialDependentRequestValidation;
    private final FinancialDependentWebTransformer financialDependentWebTransformer;
    private final FinancialDependentService financialDependentService;

    @NotNull
    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(FinancialDependentRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .doOnNext(financialDependentRequestValidation::validate)
                .map(financialDependentWebTransformer::from)
                .flatMap(financialDependentService::create)
                .map(financialDependentWebTransformer::from)
                .flatMap(financialDependentResponse ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(financialDependentResponse));
    }

    public Mono<ServerResponse> findAll() {
        final var financialDependentFlux = financialDependentService
                .findAll()
                .map(financialDependentWebTransformer::from);

        return financialDependentFlux
                .hasElements()
                .flatMap(hasElements ->
                        Boolean.TRUE.equals(hasElements)
                                ? ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(financialDependentFlux, FinancialDependentResponse.class)
                                : ServerResponse
                                .noContent()
                                .build());
    }

    @NotNull
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        final var financialDependentId = serverRequest.pathVariable("id");
        return serverRequest
                .bodyToMono(FinancialDependentRequest.class)
                .switchIfEmpty(Mono.error(() -> new ApiException(BODY_REQUIRED)))
                .map(financialDependentRequest -> financialDependentWebTransformer.from(financialDependentRequest, financialDependentId))
                .flatMap(financialDependentService::update)
                .map(financialDependentWebTransformer::from)
                .flatMap(financialDependentResponse ->
                        ServerResponse
                                .ok()
                                .bodyValue(financialDependentResponse));
    }

    @NotNull
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        final var financialDependentId = serverRequest.pathVariable("id");
        return financialDependentService.delete(financialDependentId)
                .map(financialDependentWebTransformer::from)
                .flatMap(financialDependentResponse ->
                        ServerResponse.ok()
                                .bodyValue(financialDependentResponse));

    }
}
