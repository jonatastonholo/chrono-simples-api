package dev.tonholo.chronosimplesapi.web.transformer;

import dev.tonholo.chronosimplesapi.domain.FinancialDependent;
import dev.tonholo.chronosimplesapi.service.event.FinancialDependentCreationEvent;
import dev.tonholo.chronosimplesapi.service.event.FinancialDependentUpdateEvent;
import dev.tonholo.chronosimplesapi.web.dto.FinancialDependentRequest;
import dev.tonholo.chronosimplesapi.web.dto.FinancialDependentResponse;
import org.springframework.stereotype.Service;

@Service
public class FinancialDependentWebTransformer {

    public FinancialDependentCreationEvent from(FinancialDependentRequest financialDependentRequest) {
        return FinancialDependentCreationEvent.builder()
                .name(financialDependentRequest.getName())
                .irrfDeduct(financialDependentRequest.getIrrfDeduct())
                .periodBegin(financialDependentRequest.getPeriodBegin())
                .periodEnd(financialDependentRequest.getPeriodEnd())
                .build();
    }

    public FinancialDependentUpdateEvent from(FinancialDependentRequest financialDependentRequest, String financialDependentId) {
        return FinancialDependentUpdateEvent.builder()
                .id(financialDependentId)
                .name(financialDependentRequest.getName())
                .irrfDeduct(financialDependentRequest.getIrrfDeduct())
                .periodBegin(financialDependentRequest.getPeriodBegin())
                .periodEnd(financialDependentRequest.getPeriodEnd())
                .build();
    }

    public FinancialDependentResponse from(FinancialDependent financialDependent) {
        return FinancialDependentResponse.builder()
                .id(financialDependent.getId())
                .name(financialDependent.getName())
                .irrfDeduct(financialDependent.getIrrfDeduct())
                .periodBegin(financialDependent.getPeriodBegin())
                .periodEnd(financialDependent.getPeriodEnd())
                .createdAt(financialDependent.getCreatedAt())
                .updatedAt(financialDependent.getUpdatedAt())
                .build();
    }
}
