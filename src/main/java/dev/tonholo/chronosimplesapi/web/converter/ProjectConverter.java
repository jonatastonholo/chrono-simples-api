package dev.tonholo.chronosimplesapi.web.converter;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.service.event.ProjectCreationEvent;
import dev.tonholo.chronosimplesapi.service.event.ProjectUpdateEvent;
import dev.tonholo.chronosimplesapi.web.dto.ProjectRequest;
import dev.tonholo.chronosimplesapi.web.dto.ProjectResponse;
import org.springframework.stereotype.Service;

@Service
public class ProjectConverter {

    public ProjectCreationEvent from(ProjectRequest projectRequest) {
        return ProjectCreationEvent.builder()
                .name(projectRequest.getName())
                .hourValue(projectRequest.getHourValue())
                .currencyCode(projectRequest.getCurrencyCode())
                .build();
    }

    public ProjectUpdateEvent from(ProjectRequest projectRequest, String projectId) {
        return ProjectUpdateEvent.builder()
                .id(projectId)
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
