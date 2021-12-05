package dev.tonholo.chronosimplesapi.web.converter;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.service.event.PeriodCreationEvent;
import dev.tonholo.chronosimplesapi.service.event.PeriodUpdateEvent;
import dev.tonholo.chronosimplesapi.web.dto.PeriodRequest;
import dev.tonholo.chronosimplesapi.web.dto.PeriodResponse;
import dev.tonholo.chronosimplesapi.web.dto.ProjectResponse;
import org.springframework.stereotype.Service;

@Service
public class PeriodConverter {

    public PeriodCreationEvent from(PeriodRequest periodRequest) {
        return PeriodCreationEvent.builder()
                .projectId(periodRequest.getProjectId())
                .description(periodRequest.getDescription())
                .hourValue(periodRequest.getHourValue())
                .begin(periodRequest.getBegin())
                .end(periodRequest.getEnd())
                .build();
    }

    public PeriodUpdateEvent from(PeriodRequest periodRequest, String periodId) {
        return PeriodUpdateEvent.builder()
                .id(periodId)
                .projectId(periodRequest.getProjectId())
                .description(periodRequest.getDescription())
                .hourValue(periodRequest.getHourValue())
                .currency(periodRequest.getCurrency())
                .begin(periodRequest.getBegin())
                .end(periodRequest.getEnd())
                .build();
    }

    public PeriodResponse from(Period period) {
        return PeriodResponse.builder()
                .id(period.getId())
                .project(from(period.getProject()))
                .description(period.getDescription())
                .hourValue(period.getHourValue())
                .currency(period.getCurrency())
                .begin(period.getBegin())
                .end(period.getEnd())
                .build();
    }

    private ProjectResponse from(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .hourValue(project.getHourValue())
                .currencyCode(project.getCurrencyCode())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
