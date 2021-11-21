package dev.tonholo.chronosimplesapi.web.dto;

import dev.tonholo.chronosimplesapi.domain.type.ExpenseType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class ExpenseResponse {
    String id;
    String description;
    BigDecimal value;
    ExpenseType type;
    LocalDate periodBegin;
    LocalDate periodEnd;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
