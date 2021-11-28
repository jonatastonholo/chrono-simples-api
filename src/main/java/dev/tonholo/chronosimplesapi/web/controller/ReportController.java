package dev.tonholo.chronosimplesapi.web.controller;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.service.ReportService;
import dev.tonholo.chronosimplesapi.web.converter.ReportConverter;
import dev.tonholo.chronosimplesapi.web.dto.ReportGenerationRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.BODY_REQUIRED;

@Controller
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final ReportConverter reportConverter;

    @NotNull
    public Mono<ServerResponse> generate(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(ReportGenerationRequest.class)
                .switchIfEmpty(Mono.error(new ApiException(BODY_REQUIRED)))
                .map(reportConverter::from)
                .flatMap(reportService::generate)
                .map(reportConverter::from)
                .flatMap(reportGenerationResponse ->
                        ServerResponse
                                .ok()
                                .bodyValue(reportGenerationResponse));
    }
}
