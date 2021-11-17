package dev.tonholo.chronosimplesapi.domain.event;

import dev.tonholo.chronosimplesapi.domain.type.ExpenseType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class ExpenseUpdateEvent {
    String id;
    String description;
    BigDecimal value;
    ExpenseType type;
    LocalDate periodBegin;
    LocalDate periodEnd;
}
