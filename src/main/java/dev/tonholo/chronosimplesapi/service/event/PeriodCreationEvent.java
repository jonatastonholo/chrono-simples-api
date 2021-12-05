package dev.tonholo.chronosimplesapi.service.event;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class PeriodCreationEvent {
    String projectId;
    String description;
    BigDecimal hourValue;
    CurrencyCodeType currency;
    LocalDateTime begin;
    LocalDateTime end;
}
