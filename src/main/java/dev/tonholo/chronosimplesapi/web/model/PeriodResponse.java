package dev.tonholo.chronosimplesapi.web.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class PeriodResponse {
    String projectId;
    String description;
    BigDecimal hourValue;
    LocalDateTime timerBegin;
    LocalDateTime timerEnd;
}
