package dev.tonholo.chronosimplesapi.web.transformer;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.service.event.PeriodUpdateEvent;
import dev.tonholo.chronosimplesapi.service.event.StopwatchStartEvent;
import dev.tonholo.chronosimplesapi.web.dto.PeriodRequest;
import dev.tonholo.chronosimplesapi.web.dto.StopwatchRequest;
import dev.tonholo.chronosimplesapi.web.dto.StopwatchResponse;
import org.springframework.stereotype.Service;

@Service
public class StopwatchWebTransformer {

    public StopwatchStartEvent from(StopwatchRequest stopwatchRequest) {
        return StopwatchStartEvent.builder()
                .projectId(stopwatchRequest.getProjectId())
                .description(stopwatchRequest.getDescription())
                .hourValue(stopwatchRequest.getHourValue())
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

    public StopwatchResponse from(Period period) {
        return StopwatchResponse.builder()
                .periodId(period.getId())
                .projectId(period.getProjectId())
                .description(period.getDescription())
                .hourValue(period.getHourValue())
                .begin(period.getBegin())
                .end(period.getEnd())
                .build();
    }
}
