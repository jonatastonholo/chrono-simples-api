package dev.tonholo.chronosimplesapi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static dev.tonholo.chronosimplesapi.config.JsonConfig.DATE_FORMAT;
import static dev.tonholo.chronosimplesapi.config.JsonConfig.DATE_TIME_FORMAT;

@Value
@Builder
public class FinancialDependentResponse {
    String id;
    String name;
    Boolean irrfDeduct;
    @JsonFormat(pattern=DATE_FORMAT)
    LocalDate periodBegin;
    @JsonFormat(pattern=DATE_FORMAT)
    LocalDate periodEnd;
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    LocalDateTime createdAt;
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    LocalDateTime updatedAt;
}