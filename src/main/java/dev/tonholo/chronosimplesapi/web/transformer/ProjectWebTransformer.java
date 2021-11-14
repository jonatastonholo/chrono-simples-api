package dev.tonholo.chronosimplesapi.web.transformer;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.domain.event.ProjectCreationEvent;
import dev.tonholo.chronosimplesapi.web.model.ProjectRequest;
import dev.tonholo.chronosimplesapi.web.model.ProjectResponse;
import org.springframework.stereotype.Service;

@Service
public class ProjectWebTransformer {

    public ProjectCreationEvent from(ProjectRequest projectRequest) {
        return ProjectCreationEvent.builder()
                .name(projectRequest.getName())
                .hourValue(projectRequest.getHourValue())
                .currencyCode(projectRequest.getCurrencyCode())
                .build();
    }

    public ProjectResponse from(Project project) {
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
