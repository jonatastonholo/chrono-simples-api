package dev.tonholo.chronosimplesapi.domain.event;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class FinancialDependentCreationEvent {
    String name;
    Boolean irrfDeduct;
    LocalDate periodBegin;
    LocalDate periodEnd;
}