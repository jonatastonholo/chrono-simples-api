package dev.tonholo.chronosimplesapi.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Period {
    private String id;
    private String projectId;
    private BigDecimal hourValue;
    private LocalDateTime begin;
    private LocalDateTime end;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
