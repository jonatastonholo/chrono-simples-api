package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.ProjectEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

interface ProjectReactiveRepository extends ReactiveCrudRepository<ProjectEntity, String> {

    @Query("SELECT * FROM db_chrono_simples.tb_project WHERE name = $1 AND deleted IS FALSE")
    Mono<Project> findByName(String name);
}
