package dev.tonholo.chronosimplesapi.web.model;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class ProjectResponse {
    String id;
    String name;
    BigDecimal hourValue;
    CurrencyCodeType currencyCode;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
