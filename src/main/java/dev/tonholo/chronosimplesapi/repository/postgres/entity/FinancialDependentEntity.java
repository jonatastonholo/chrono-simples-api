package dev.tonholo.chronosimplesapi.repository.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("db_chrono_simples.tb_financial_dependent")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialDependentEntity {
    @Id private String id;
    private String name;
    private Boolean irrfDeduct;
    private LocalDate periodBegin;
    private LocalDate periodEnd;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
}
