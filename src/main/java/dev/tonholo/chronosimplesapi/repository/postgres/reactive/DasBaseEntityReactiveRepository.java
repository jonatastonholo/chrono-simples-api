package dev.tonholo.chronosimplesapi.repository.postgres.reactive;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.DasBaseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DasBaseEntityReactiveRepository extends ReactiveCrudRepository<DasBaseEntity, Integer> {
}
