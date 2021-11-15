package dev.tonholo.chronosimplesapi.web.transformer;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.domain.event.PeriodCreationEvent;
import dev.tonholo.chronosimplesapi.domain.event.PeriodUpdateEvent;
import dev.tonholo.chronosimplesapi.web.model.PeriodRequest;
import dev.tonholo.chronosimplesapi.web.model.PeriodResponse;
import org.springframework.stereotype.Service;

@Service
public class PeriodWebTransformer {

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
                .begin(periodRequest.getBegin())
                .end(periodRequest.getEnd())
                .build();
    }

    public PeriodResponse from(Period period) {
        return PeriodResponse.builder()
                .id(period.getId())
                .projectId(period.getProjectId())
                .description(period.getDescription())
                .hourValue(period.getHourValue())
                .begin(period.getBegin())
                .end(period.getEnd())
                .build();
    }
}
