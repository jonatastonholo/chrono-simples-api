package dev.tonholo.chronosimplesapi.web.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class FinancialDependentResponse {
    String id;
    String name;
    Boolean irrfDeduct;
    LocalDate periodBegin;
    LocalDate periodEnd;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}