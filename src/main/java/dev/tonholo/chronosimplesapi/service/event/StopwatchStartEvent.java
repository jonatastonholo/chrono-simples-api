package dev.tonholo.chronosimplesapi.service.event;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class StopwatchStartEvent {
    String projectId;
    String description;
    BigDecimal hourValue;
}
