package dev.tonholo.chronosimplesapi.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportGenerationRequest {
    private LocalDate periodBegin;
    private LocalDate periodEnd;

    @JsonDeserialize(as = BigDecimal.class)
    private BigDecimal rFactor;

    public Optional<BigDecimal> getRFactor() {
        return Optional.ofNullable(rFactor);
    }
}
