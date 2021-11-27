package dev.tonholo.chronosimplesapi.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TaxCalculated {
    private String id;
    private LocalDateTime periodBegin;
    private LocalDateTime periodEnd;
    private BigDecimal periodEarnings;
    private BigDecimal baseProLabor;
    private BigDecimal rFactor;
    private Integer financialDependents;
    private BigDecimal financialDependentsDeduction;
    private BigDecimal inssAmount;
    private BigDecimal irrfAmount;
    private BigDecimal dasAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
