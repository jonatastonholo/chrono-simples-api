package dev.tonholo.chronosimplesapi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static dev.tonholo.chronosimplesapi.config.JsonConfig.DATE_TIME_FORMAT;

@Value
@Builder
public class StopwatchResponse {
    String periodId;
    String projectId;
    String description;
    BigDecimal hourValue;
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    LocalDateTime begin;
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    LocalDateTime end;
}
