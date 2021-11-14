package dev.tonholo.chronosimplesapi.service.transformer;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.domain.event.ProjectCreationEvent;
import org.springframework.stereotype.Service;

@Service
public class ProjectTransformer {

    public Project from(ProjectCreationEvent projectCreationEvent) {
        return Project.builder()
                .name(projectCreationEvent.getName())
                .hourValue(projectCreationEvent.getHourValue())
                .currencyCode(projectCreationEvent.getCurrencyCode())
                .build();
    }
}
