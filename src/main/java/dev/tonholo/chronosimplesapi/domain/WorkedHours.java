package dev.tonholo.chronosimplesapi.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkedHours {
    private Long hours;
    private Long minutes;
    private Long seconds;
    private Long timeElapsedInMillis;
}
