package dev.tonholo.chronosimplesapi.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StopwatchRequest {
    private String projectId;
    private String description;
    private BigDecimal hourValue;
}
