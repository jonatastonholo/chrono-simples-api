package dev.tonholo.chronosimplesapi.repository.postgres.mapper;

import dev.tonholo.chronosimplesapi.domain.FinancialDependent;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.FinancialDependentEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FinancialDependentMapper {

    public FinancialDependentEntity from(FinancialDependent financialDependent) {
        return FinancialDependentEntity.builder()
                .id(financialDependent.getId())
                .name(financialDependent.getName())
                .irrfDeduct(financialDependent.getIrrfDeduct())
                .periodBegin(financialDependent.getPeriodBegin())
                .periodEnd(financialDependent.getPeriodEnd())
                .createdAt(financialDependent.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public FinancialDependent from(FinancialDependentEntity financialDependentEntity) {
        return FinancialDependent.builder()
                .id(financialDependentEntity.getId())
                .name(financialDependentEntity.getName())
                .irrfDeduct(financialDependentEntity.getIrrfDeduct())
                .periodBegin(financialDependentEntity.getPeriodBegin())
                .periodEnd(financialDependentEntity.getPeriodEnd())
                .createdAt(financialDependentEntity.getCreatedAt())
                .updatedAt(financialDependentEntity.getUpdatedAt())
                .build();
    }
}
