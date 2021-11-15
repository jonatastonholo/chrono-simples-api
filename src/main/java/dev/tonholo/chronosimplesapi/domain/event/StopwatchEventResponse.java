package dev.tonholo.chronosimplesapi.domain.event;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class StopwatchEventResponse {
    LocalDateTime stopwatchBegin;
    Long days;
    Long hours;
    Long minutes;
    Long seconds;
    Long millis;
}
