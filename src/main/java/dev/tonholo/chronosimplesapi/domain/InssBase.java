package dev.tonholo.chronosimplesapi.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class InssBase {
    private Integer id;
    private BigDecimal baseValueRangeBegin;
    private BigDecimal baseValueRangeEnd;
    private BigDecimal basePercentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
