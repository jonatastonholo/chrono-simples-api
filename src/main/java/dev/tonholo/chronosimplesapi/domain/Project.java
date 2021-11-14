package dev.tonholo.chronosimplesapi.domain;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Project {
    private String id;
    private String name;
    private BigDecimal hourValue;
    private CurrencyCodeType currencyCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
