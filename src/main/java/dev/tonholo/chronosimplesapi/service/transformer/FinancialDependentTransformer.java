package dev.tonholo.chronosimplesapi.service.transformer;

import dev.tonholo.chronosimplesapi.domain.FinancialDependent;
import dev.tonholo.chronosimplesapi.domain.event.FinancialDependentCreationEvent;
import dev.tonholo.chronosimplesapi.domain.event.FinancialDependentUpdateEvent;
import org.springframework.stereotype.Service;

@Service
public class FinancialDependentTransformer {

    public FinancialDependent from(FinancialDependentCreationEvent financialDependentCreationEvent) {
        return FinancialDependent.builder()
                .name(financialDependentCreationEvent.getName())
                .irrfDeduct(financialDependentCreationEvent.getIrrfDeduct())
                .periodBegin(financialDependentCreationEvent.getPeriodBegin())
                .periodEnd(financialDependentCreationEvent.getPeriodEnd())
                .build();
    }

    public FinancialDependent from(FinancialDependentUpdateEvent financialDependentUpdateEvent) {
        return FinancialDependent.builder()
                .id(financialDependentUpdateEvent.getId())
                .name(financialDependentUpdateEvent.getName())
                .irrfDeduct(financialDependentUpdateEvent.getIrrfDeduct())
                .periodBegin(financialDependentUpdateEvent.getPeriodBegin())
                .periodEnd(financialDependentUpdateEvent.getPeriodEnd())
                .build();
    }
}
