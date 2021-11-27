package dev.tonholo.chronosimplesapi.repository.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("db_chrono_simples.tb_tax_calculated")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaxCalculatedEntity {
    @Id private String id;
    private LocalDateTime periodBegin;
    private LocalDateTime periodEnd;
    private BigDecimal lastYearEarnings;
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
    private Boolean deleted;
}
