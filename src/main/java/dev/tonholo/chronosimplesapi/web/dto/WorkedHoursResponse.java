package dev.tonholo.chronosimplesapi.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WorkedHoursResponse {
    Long hours;
    Long minutes;
    Long seconds;
    Long timeElapsedInMillis;
}
