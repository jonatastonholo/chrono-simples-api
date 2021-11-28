package dev.tonholo.chronosimplesapi.service.event;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Value
@Builder
public class TaxCalculationEvent {
    LocalDateTime periodBegin;
    LocalDateTime periodEnd;
    BigDecimal rFactor;

    public Optional<BigDecimal> getRFactor() {
        return Optional.ofNullable(rFactor);
    }
}
