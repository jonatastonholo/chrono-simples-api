package dev.tonholo.chronosimplesapi.web.converter;

import dev.tonholo.chronosimplesapi.domain.WorkedHours;
import dev.tonholo.chronosimplesapi.service.event.ReportGenerationEvent;
import dev.tonholo.chronosimplesapi.service.event.ReportGenerationResultEvent;
import dev.tonholo.chronosimplesapi.web.dto.ReportGenerationRequest;
import dev.tonholo.chronosimplesapi.web.dto.ReportGenerationResponse;
import dev.tonholo.chronosimplesapi.web.dto.WorkedHoursResponse;
import org.springframework.stereotype.Service;

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
                .baseProLabor(reportGenerationResultEvent.getBaseProLabor())
                .rFactor(reportGenerationResultEvent.getRFactor())
                .financialDependents(reportGenerationResultEvent.getFinancialDependents())
                .financialDependentsDeduction(reportGenerationResultEvent.getFinancialDependentsDeduction())
                .inssAmount(reportGenerationResultEvent.getInssAmount())
                .irrfAmount(reportGenerationResultEvent.getIrrfAmount())
                .dasAmount(reportGenerationResultEvent.getDasAmount())
                .workedHours(from(reportGenerationResultEvent.getWorkedHours()))
                .build();
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
