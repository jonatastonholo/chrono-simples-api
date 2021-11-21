package dev.tonholo.chronosimplesapi.service.event;

import dev.tonholo.chronosimplesapi.domain.type.ExpenseType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class ExpenseCreationEvent {
    String description;
    BigDecimal value;
    ExpenseType type;
    LocalDate periodBegin;
    LocalDate periodEnd;
}
