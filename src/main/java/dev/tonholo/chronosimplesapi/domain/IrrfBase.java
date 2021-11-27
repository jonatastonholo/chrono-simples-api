package dev.tonholo.chronosimplesapi.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class IrrfBase {
    private Integer id;
    private BigDecimal baseValueRangeBegin;
    private BigDecimal baseValueRangeEnd;
    private BigDecimal aliquot;
    private BigDecimal deduction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
