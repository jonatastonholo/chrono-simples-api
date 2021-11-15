package dev.tonholo.chronosimplesapi.service.transformer;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.domain.event.PeriodCreationEvent;
import dev.tonholo.chronosimplesapi.domain.event.PeriodUpdateEvent;
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

    public Period from(PeriodUpdateEvent periodUpdateEvent) {
        return Period.builder()
                .id(periodUpdateEvent.getId())
                .projectId(periodUpdateEvent.getProjectId())
                .hourValue(periodUpdateEvent.getHourValue())
                .begin(periodUpdateEvent.getBegin())
                .end(periodUpdateEvent.getEnd())
                .description(periodUpdateEvent.getDescription())
                .build();
    }
}
