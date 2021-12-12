package dev.tonholo.chronosimplesapi.web.converter;

import dev.tonholo.chronosimplesapi.domain.Expense;
import dev.tonholo.chronosimplesapi.domain.WorkedHours;
import dev.tonholo.chronosimplesapi.service.event.ReportGenerationEvent;
import dev.tonholo.chronosimplesapi.service.event.ReportGenerationResultEvent;
import dev.tonholo.chronosimplesapi.web.dto.ExpenseResponse;
import dev.tonholo.chronosimplesapi.web.dto.ReportGenerationRequest;
import dev.tonholo.chronosimplesapi.web.dto.ReportGenerationResponse;
import dev.tonholo.chronosimplesapi.web.dto.WorkedHoursResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportConverter {

    public ReportGenerationEvent from(ReportGenerationRequest reportGenerationRequest) {
        return ReportGenerationEvent.builder()
                .periodBegin(reportGenerationRequest.getPeriodBegin())
                .periodEnd(reportGenerationRequest.getPeriodEnd())
                .rFactor(reportGenerationRequest.getRFactor().orElse(null))
                .build();
    }

    public ReportGenerationResponse from(ReportGenerationResultEvent reportGenerationResultEvent) {
        return ReportGenerationResponse.builder()
                .periodBegin(reportGenerationResultEvent.getPeriodBegin())
                .periodEnd(reportGenerationResultEvent.getPeriodEnd())
                .last12MonthEarnings(reportGenerationResultEvent.getLast12MonthEarnings())
                .periodEarnings(reportGenerationResultEvent.getPeriodEarnings())
                .liquidPeriodEarnings(reportGenerationResultEvent.getLiquidPeriodEarnings())
                .profitToWithdrawal(reportGenerationResultEvent.getProfitToWithdrawal())
                .baseProLabor(reportGenerationResultEvent.getBaseProLabor())
                .liquidProLabor(reportGenerationResultEvent.getLiquidProLabor())
                .proLaborToWithdrawal(reportGenerationResultEvent.getProLaborToWithdrawal())
                .rFactor(reportGenerationResultEvent.getRFactor())
                .financialDependents(reportGenerationResultEvent.getFinancialDependents())
                .financialDependentsDeduction(reportGenerationResultEvent.getFinancialDependentsDeduction())
                .inssAmount(reportGenerationResultEvent.getInssAmount())
                .irrfAmount(reportGenerationResultEvent.getIrrfAmount())
                .dasAmount(reportGenerationResultEvent.getDasAmount())
                .totalAmountToWithdrawal(reportGenerationResultEvent.getTotalAmountToWithdrawal())
                .amountToKeep(reportGenerationResultEvent.getAmountToKeep())
                .workedHours(from(reportGenerationResultEvent.getWorkedHours()))
                .expenses(from(reportGenerationResultEvent.getExpenses()))
                .build();
    }

    private List<ExpenseResponse> from(List<Expense> expenses) {
        return expenses
                .stream()
                .map(expense
                        -> ExpenseResponse.builder()
                        .id(expense.getId())
                        .description(expense.getDescription())
                        .value(expense.getValue())
                        .type(expense.getType())
                        .periodBegin(expense.getPeriodBegin())
                        .periodEnd(expense.getPeriodEnd())
                        .createdAt(expense.getCreatedAt())
                        .updatedAt(expense.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private WorkedHoursResponse from(WorkedHours workedHours) {
        return WorkedHoursResponse.builder()
                .hours(workedHours.getHours())
                .minutes(workedHours.getMinutes())
                .seconds(workedHours.getSeconds())
                .timeElapsedInMillis(workedHours.getTimeElapsedInMillis())
                .build();
    }
}
