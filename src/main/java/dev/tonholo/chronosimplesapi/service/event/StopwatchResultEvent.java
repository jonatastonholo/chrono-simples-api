package dev.tonholo.chronosimplesapi.service.event;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class StopwatchResultEvent {
    String projectId;
    LocalDateTime stopwatchBegin;
    Long days;
    Long hours;
    Long minutes;
    Long seconds;
    Long millis;
}
