package dev.tonholo.chronosimplesapi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static dev.tonholo.chronosimplesapi.config.JsonConfig.DATE_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodRequest {
    private String projectId;
    private String description;
    private BigDecimal hourValue;
    private CurrencyCodeType currency;
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    private LocalDateTime begin;
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    private LocalDateTime end;
}
