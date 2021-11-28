package dev.tonholo.chronosimplesapi.service;

import dev.tonholo.chronosimplesapi.service.event.ReportGenerationEvent;
import dev.tonholo.chronosimplesapi.service.event.ReportGenerationResultEvent;
import dev.tonholo.chronosimplesapi.service.transformer.ReportTransformer;
import dev.tonholo.chronosimplesapi.service.validation.ReportGenerationEventValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final TaxesService taxesService;
    private final ReportGenerationEventValidation reportGenerationEventValidation;
    private final ReportTransformer reportTransformer;

    public Mono<ReportGenerationResultEvent> generate(ReportGenerationEvent reportGenerationEvent) {
        log.info("Generating report -> {}", reportGenerationEvent );
        return Mono.just(reportGenerationEvent)
                .doOnNext(reportGenerationEventValidation::validate)
                .map(reportTransformer::from)
                .flatMap(taxesService::calculate)
                .map(reportTransformer::from);
    }
}
