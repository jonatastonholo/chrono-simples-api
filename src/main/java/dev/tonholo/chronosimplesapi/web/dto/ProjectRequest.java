package dev.tonholo.chronosimplesapi.web.dto;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String name;
    private BigDecimal hourValue;
    private CurrencyCodeType currencyCode;
}
