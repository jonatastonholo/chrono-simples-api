package dev.tonholo.chronosimplesapi.service.transformer;

import dev.tonholo.chronosimplesapi.service.event.ReportGenerationEvent;
import dev.tonholo.chronosimplesapi.service.event.ReportGenerationResultEvent;
import dev.tonholo.chronosimplesapi.service.event.TaxCalculationEvent;
import dev.tonholo.chronosimplesapi.service.event.TaxCalculationResultEvent;
import org.springframework.stereotype.Service;

@Service
public class ReportTransformer {

    public TaxCalculationEvent from(ReportGenerationEvent reportGenerationEvent) {
        return TaxCalculationEvent.builder()
                .periodBegin(reportGenerationEvent.getPeriodBegin())
                .periodEnd(reportGenerationEvent.getPeriodEnd())
                .rFactor(reportGenerationEvent.getRFactor().orElse(null))
                .build();
    }

    public ReportGenerationResultEvent from(TaxCalculationResultEvent taxCalculationResultEvent) {
        return ReportGenerationResultEvent.builder()
                .periodBegin(taxCalculationResultEvent.getPeriodBegin())
                .periodEnd(taxCalculationResultEvent.getPeriodEnd())
                .last12MonthEarnings(taxCalculationResultEvent.getLast12MonthEarnings())
                .periodEarnings(taxCalculationResultEvent.getPeriodEarnings())
                .liquidPeriodEarnings(taxCalculationResultEvent.getLiquidPeriodEarnings())
                .profitToWithdrawal(taxCalculationResultEvent.getProfitToWithdrawal())
                .baseProLabor(taxCalculationResultEvent.getBaseProLabor())
                .liquidProLabor(taxCalculationResultEvent.getLiquidProLabor())
                .proLaborToWithdrawal(taxCalculationResultEvent.getProLaborToWithdrawal())
                .rFactor(taxCalculationResultEvent.getRFactor())
                .financialDependents(taxCalculationResultEvent.getFinancialDependents())
                .financialDependentsDeduction(taxCalculationResultEvent.getFinancialDependentsDeduction())
                .inssAmount(taxCalculationResultEvent.getInssAmount())
                .irrfAmount(taxCalculationResultEvent.getIrrfAmount())
                .dasAmount(taxCalculationResultEvent.getDasAmount())
                .totalAmountToWithdrawal(taxCalculationResultEvent.getTotalAmountToWithdrawal())
                .amountToKeep(taxCalculationResultEvent.getAmountToKeep())
                .workedHours(taxCalculationResultEvent.getWorkedHours())
                .expenses(taxCalculationResultEvent.getExpenses())
                .build();
    }
}
