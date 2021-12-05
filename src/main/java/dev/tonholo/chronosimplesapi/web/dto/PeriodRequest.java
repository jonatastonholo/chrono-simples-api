package dev.tonholo.chronosimplesapi.web.dto;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodRequest {
    private String projectId;
    private String description;
    private BigDecimal hourValue;
    private CurrencyCodeType currency;
    private LocalDateTime begin;
    private LocalDateTime end;
}
