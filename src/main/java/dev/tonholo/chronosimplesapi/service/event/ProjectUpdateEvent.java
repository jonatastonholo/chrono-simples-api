package dev.tonholo.chronosimplesapi.service.event;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProjectUpdateEvent {
    String id;
    String name;
    BigDecimal hourValue;
    CurrencyCodeType currencyCode;
}
