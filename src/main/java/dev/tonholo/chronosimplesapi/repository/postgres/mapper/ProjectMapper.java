package dev.tonholo.chronosimplesapi.repository.postgres.mapper;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.ProjectEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProjectMapper {

    public ProjectEntity from(Project project) {
        return ProjectEntity.builder()
                .id(project.getId())
                .name(project.getName())
                .hourValue(project.getHourValue())
                .currencyCode(project.getCurrencyCode())
                .createdAt(project.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Project from(ProjectEntity projectEntity) {
        return Project.builder()
                .id(projectEntity.getId())
                .name(projectEntity.getName())
                .hourValue(projectEntity.getHourValue())
                .currencyCode(projectEntity.getCurrencyCode())
                .createdAt(projectEntity.getCreatedAt())
                .updatedAt(projectEntity.getUpdatedAt())
                .build();
    }
}
