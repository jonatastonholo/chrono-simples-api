package dev.tonholo.chronosimplesapi.repository.postgres.entity;

import dev.tonholo.chronosimplesapi.domain.type.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("db_chrono_simples.tb_expense")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseEntity {
    @Id private String id;
    private String description;
    private BigDecimal value;
    private ExpenseType type;
    private LocalDate periodBegin;
    private LocalDate periodEnd;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
}
