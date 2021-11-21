package dev.tonholo.chronosimplesapi.service.event;

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
    LocalDateTime begin;
    LocalDateTime end;
}
