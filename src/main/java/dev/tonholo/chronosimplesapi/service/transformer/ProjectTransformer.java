package dev.tonholo.chronosimplesapi.service.transformer;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.domain.event.ProjectCreationEvent;
import dev.tonholo.chronosimplesapi.domain.event.ProjectUpdateEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProjectTransformer {

    public Project from(ProjectCreationEvent projectCreationEvent) {
        return Project.builder()
                .name(projectCreationEvent.getName())
                .hourValue(projectCreationEvent.getHourValue())
                .currencyCode(projectCreationEvent.getCurrencyCode())
                .build();
    }

    public Project from(ProjectUpdateEvent projectUpdateEvent) {
        return Project.builder()
                .id(projectUpdateEvent.getId())
                .name(projectUpdateEvent.getName())
                .hourValue(projectUpdateEvent.getHourValue())
                .currencyCode(projectUpdateEvent.getCurrencyCode())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
