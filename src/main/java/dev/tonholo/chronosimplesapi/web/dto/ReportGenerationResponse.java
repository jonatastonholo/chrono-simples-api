package dev.tonholo.chronosimplesapi.web.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class ReportGenerationResponse {
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
    WorkedHoursResponse workedHours;
}
