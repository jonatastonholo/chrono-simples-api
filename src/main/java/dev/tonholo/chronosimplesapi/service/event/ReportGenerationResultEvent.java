package dev.tonholo.chronosimplesapi.service.event;

import dev.tonholo.chronosimplesapi.domain.WorkedHours;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class ReportGenerationResultEvent {
    LocalDateTime periodBegin;
    LocalDateTime periodEnd;
    BigDecimal last12MonthEarnings;
    BigDecimal periodEarnings;
    BigDecimal baseProLabor;
    BigDecimal rFactor;
    Integer financialDependents;
    BigDecimal financialDependentsDeduction;
    BigDecimal inssAmount;
    BigDecimal irrfAmount;
    BigDecimal dasAmount;
    WorkedHours workedHours;
}
