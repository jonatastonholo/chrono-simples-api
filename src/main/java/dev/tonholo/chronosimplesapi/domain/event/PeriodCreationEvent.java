package dev.tonholo.chronosimplesapi.domain.event;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class PeriodCreationEvent {
    String projectId;
    String description;
    BigDecimal hourValue;
    LocalDateTime timerBegin;
    LocalDateTime timerEnd;
}
