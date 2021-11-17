package dev.tonholo.chronosimplesapi.web.model;

import dev.tonholo.chronosimplesapi.domain.type.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    private String description;
    private BigDecimal value;
    private ExpenseType type;
    private LocalDate periodBegin;
    private LocalDate periodEnd;
}
