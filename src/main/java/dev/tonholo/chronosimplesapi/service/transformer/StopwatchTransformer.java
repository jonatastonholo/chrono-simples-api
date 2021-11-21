package dev.tonholo.chronosimplesapi.service.transformer;

import dev.tonholo.chronosimplesapi.service.event.PeriodCreationEvent;
import dev.tonholo.chronosimplesapi.service.event.StopwatchStartEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StopwatchTransformer {

    public PeriodCreationEvent from(StopwatchStartEvent stopwatchStartEvent) {
        return PeriodCreationEvent.builder()
                .projectId(stopwatchStartEvent.getProjectId())
                .description(stopwatchStartEvent.getDescription())
                .hourValue(stopwatchStartEvent.getHourValue())
                .begin(LocalDateTime.now())
                .build();
    }
}
