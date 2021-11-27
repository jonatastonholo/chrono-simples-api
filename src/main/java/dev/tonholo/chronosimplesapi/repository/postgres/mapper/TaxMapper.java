package dev.tonholo.chronosimplesapi.repository.postgres.mapper;

import dev.tonholo.chronosimplesapi.domain.DasBase;
import dev.tonholo.chronosimplesapi.domain.InssBase;
import dev.tonholo.chronosimplesapi.domain.IrrfBase;
import dev.tonholo.chronosimplesapi.domain.TaxCalculated;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.DasBaseEntity;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.InssBaseEntity;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.IrrfBaseEntity;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.TaxCalculatedEntity;
import org.springframework.stereotype.Service;

@Service
public class TaxMapper {

    public InssBase from (InssBaseEntity inssBaseEntity) {
        return InssBase.builder()
                .id(inssBaseEntity.getId())
                .baseValueRangeBegin(inssBaseEntity.getBaseValueRangeBegin())
                .baseValueRangeEnd(inssBaseEntity.getBaseValueRangeEnd())
                .basePercentage(inssBaseEntity.getBasePercentage())
                .createdAt(inssBaseEntity.getCreatedAt())
                .updatedAt(inssBaseEntity.getUpdatedAt())
                .build();
    }

    public IrrfBase from (IrrfBaseEntity irrfBaseEntity) {
           return IrrfBase.builder()
                   .id(irrfBaseEntity.getId())
                   .baseValueRangeBegin(irrfBaseEntity.getBaseValueRangeBegin())
                   .baseValueRangeEnd(irrfBaseEntity.getBaseValueRangeEnd())
                   .aliquot(irrfBaseEntity.getAliquot())
                   .deduction(irrfBaseEntity.getDeduction())
                   .createdAt(irrfBaseEntity.getCreatedAt())
                   .updatedAt(irrfBaseEntity.getUpdatedAt())
                   .build();
    }

    public DasBase from (DasBaseEntity dasBaseEntity) {
        return DasBase.builder()
                .id(dasBaseEntity.getId())
                .baseValueRangeBegin(dasBaseEntity.getBaseValueRangeBegin())
                .baseValueRangeEnd(dasBaseEntity.getBaseValueRangeEnd())
                .aliquot(dasBaseEntity.getAliquot())
                .deduction(dasBaseEntity.getDeduction())
                .createdAt(dasBaseEntity.getCreatedAt())
                .updatedAt(dasBaseEntity.getUpdatedAt())
                .build();
    }

    public TaxCalculated from(TaxCalculatedEntity taxCalculatedEntity) {
        return TaxCalculated.builder()
                .id(taxCalculatedEntity.getId())
                .periodBegin(taxCalculatedEntity.getPeriodBegin())
                .periodEnd(taxCalculatedEntity.getPeriodEnd())
                .periodEarnings(taxCalculatedEntity.getPeriodEarnings())
                .baseProLabor(taxCalculatedEntity.getBaseProLabor())
                .rFactor(taxCalculatedEntity.getRFactor())
                .financialDependents(taxCalculatedEntity.getFinancialDependents())
                .financialDependentsDeduction(taxCalculatedEntity.getFinancialDependentsDeduction())
                .inssAmount(taxCalculatedEntity.getInssAmount())
                .irrfAmount(taxCalculatedEntity.getIrrfAmount())
                .dasAmount(taxCalculatedEntity.getDasAmount())
                .createdAt(taxCalculatedEntity.getCreatedAt())
                .updatedAt(taxCalculatedEntity.getUpdatedAt())
                .build();
    }

    public TaxCalculatedEntity from(TaxCalculated taxCalculated) {
        return TaxCalculatedEntity.builder()
                .id(taxCalculated.getId())
                .periodBegin(taxCalculated.getPeriodBegin())
                .periodEnd(taxCalculated.getPeriodEnd())
                .periodEarnings(taxCalculated.getPeriodEarnings())
                .baseProLabor(taxCalculated.getBaseProLabor())
                .rFactor(taxCalculated.getRFactor())
                .financialDependents(taxCalculated.getFinancialDependents())
                .financialDependentsDeduction(taxCalculated.getFinancialDependentsDeduction())
                .inssAmount(taxCalculated.getInssAmount())
                .irrfAmount(taxCalculated.getIrrfAmount())
                .dasAmount(taxCalculated.getDasAmount())
                .createdAt(taxCalculated.getCreatedAt())
                .updatedAt(taxCalculated.getUpdatedAt())
                .build();
    }
}
