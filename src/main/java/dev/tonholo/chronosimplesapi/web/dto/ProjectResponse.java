package dev.tonholo.chronosimplesapi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static dev.tonholo.chronosimplesapi.config.JsonConfig.DATE_TIME_FORMAT;

@Value
@Builder
public class ProjectResponse {
    String id;
    String name;
    BigDecimal hourValue;
    CurrencyCodeType currencyCode;
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    LocalDateTime createdAt;
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    LocalDateTime updatedAt;
}
