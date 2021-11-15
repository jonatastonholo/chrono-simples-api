package dev.tonholo.chronosimplesapi.service.transformer;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.domain.event.PeriodCreationEvent;
import org.springframework.stereotype.Service;

@Service
public class PeriodTransformer {

    public Period from(PeriodCreationEvent periodCreationEvent) {
        return Period.builder()
                .projectId(periodCreationEvent.getProjectId())
                .hourValue(periodCreationEvent.getHourValue())
                .begin(periodCreationEvent.getBegin())
                .end(periodCreationEvent.getEnd())
                .description(periodCreationEvent.getDescription())
                .build();
    }
}
