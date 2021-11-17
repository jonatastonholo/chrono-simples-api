package dev.tonholo.chronosimplesapi.domain;

import dev.tonholo.chronosimplesapi.domain.type.ExpenseType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Expense {
    private String id;
    private String description;
    private BigDecimal value;
    private ExpenseType type;
    private LocalDate periodBegin;
    private LocalDate periodEnd;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Expense completeFrom(Expense source) {
        id = (id == null) ? source.id : id;
        description = (description == null) ? source.description : description;
        value = (value == null) ? source.value : value;
        type = (type == null) ? source.type : type;
        periodBegin = (periodBegin == null) ? source.periodBegin : periodBegin;
        periodEnd = (periodEnd == null) ? source.periodEnd : periodEnd;
        createdAt = (createdAt == null) ? source.createdAt : createdAt;
        updatedAt = (updatedAt == null) ? source.updatedAt : updatedAt;
        return this;
    }
}
