package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.repository.postgres.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectRepository {
    private final ProjectReactiveRepository projectReactiveRepository;
    private final ProjectMapper projectMapper;

    public Mono<Project> save(Project project) {
        log.debug("Saving project -> {}", project);
        return Mono.just(projectMapper.from(project))
                .flatMap(projectReactiveRepository::save)
                .map(projectMapper::from);
    }

    public Mono<Project> findById(String projectId) {
        return projectReactiveRepository.findById(projectId)
                .map(projectMapper::from);
    }

    public Mono<Project> findByName(String name) {
        return projectReactiveRepository.findByName(name)
                .map(projectMapper::from);
    }

    public Flux<Project> listAll() {
        return projectReactiveRepository.findAllNotDeleted()
                .map(projectMapper::from);
    }
}
