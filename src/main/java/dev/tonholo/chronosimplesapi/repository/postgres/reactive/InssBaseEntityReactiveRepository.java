package dev.tonholo.chronosimplesapi.repository.postgres.reactive;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.InssBaseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface InssBaseEntityReactiveRepository extends ReactiveCrudRepository<InssBaseEntity, Integer> {
}
