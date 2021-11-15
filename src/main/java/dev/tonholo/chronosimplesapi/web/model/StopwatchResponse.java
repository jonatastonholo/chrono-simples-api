package dev.tonholo.chronosimplesapi.web.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class StopwatchResponse {
    String periodId;
    String projectId;
    String description;
    BigDecimal hourValue;
    LocalDateTime begin;
    LocalDateTime end;
}
