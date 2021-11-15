package dev.tonholo.chronosimplesapi.web.model;

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
public class PeriodCreationRequest {
    private String projectId;
    private String description;
    private BigDecimal hourValue;
    private LocalDateTime begin;
    private LocalDateTime end;
}
