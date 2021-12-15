package dev.tonholo.chronosimplesapi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static dev.tonholo.chronosimplesapi.config.JsonConfig.DATE_TIME_FORMAT;

@Value
@Builder
public class ReportGenerationResponse {
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    LocalDateTime periodBegin;
    @JsonFormat(pattern=DATE_TIME_FORMAT)
    LocalDateTime periodEnd;
    BigDecimal last12MonthEarnings;
    BigDecimal periodEarnings;
    BigDecimal liquidPeriodEarnings;
    BigDecimal profitToWithdrawal;
    BigDecimal baseProLabor;
    BigDecimal liquidProLabor;
    BigDecimal proLaborToWithdrawal;
    @JsonProperty("rFactor") BigDecimal rFactor;
    Integer financialDependents;
    BigDecimal financialDependentsDeduction;
    BigDecimal inssAmount;
    BigDecimal irrfAmount;
    BigDecimal dasAmount;
    BigDecimal totalAmountToWithdrawal;
    BigDecimal amountToKeep;
    WorkedHoursResponse workedHours;
    @JsonProperty("expenses") List<ExpenseResponse> expenses;
}
