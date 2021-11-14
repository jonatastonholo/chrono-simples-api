package dev.tonholo.chronosimplesapi.web.transformer;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.domain.event.PeriodCreationEvent;
import dev.tonholo.chronosimplesapi.web.model.PeriodCreationRequest;
import dev.tonholo.chronosimplesapi.web.model.PeriodResponse;
import org.springframework.stereotype.Service;

@Service
public class PeriodWebTransformer {

    public PeriodCreationEvent from(PeriodCreationRequest periodCreationRequest) {
        return PeriodCreationEvent.builder()
                .projectId(periodCreationRequest.getProjectId())
                .description(periodCreationRequest.getDescription())
                .hourValue(periodCreationRequest.getHourValue())
                .timerBegin(periodCreationRequest.getTimerBegin())
                .timerEnd(periodCreationRequest.getTimerEnd())
                .build();
    }

    public PeriodResponse from(Period periodCreationRequest) {
        return PeriodResponse.builder()
                .projectId(periodCreationRequest.getProjectId())
                .description(periodCreationRequest.getDescription())
                .hourValue(periodCreationRequest.getHourValue())
                .timerBegin(periodCreationRequest.getBegin())
                .timerEnd(periodCreationRequest.getEnd())
                .build();
    }
}
