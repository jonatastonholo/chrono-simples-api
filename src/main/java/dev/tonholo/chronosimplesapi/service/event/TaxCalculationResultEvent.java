package dev.tonholo.chronosimplesapi.service.event;

import dev.tonholo.chronosimplesapi.domain.Expense;
import dev.tonholo.chronosimplesapi.domain.WorkedHours;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaxCalculationResultEvent {
    LocalDateTime periodBegin;
    LocalDateTime periodEnd;
    BigDecimal last12MonthEarnings;
    BigDecimal periodEarnings;
    BigDecimal liquidPeriodEarnings;
    BigDecimal profitToWithdrawal;
    BigDecimal baseProLabor;
    BigDecimal liquidProLabor;
    BigDecimal proLaborToWithdrawal;
    BigDecimal rFactor;
    Integer financialDependents;
    BigDecimal financialDependentsDeduction;
    BigDecimal inssAmount;
    BigDecimal irrfAmount;
    BigDecimal dasAmount;
    BigDecimal totalAmountToWithdrawal;
    BigDecimal amountToKeep;
    WorkedHours workedHours;
    List<Expense> expenses;
}
