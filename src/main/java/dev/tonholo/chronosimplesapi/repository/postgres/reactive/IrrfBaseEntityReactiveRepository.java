package dev.tonholo.chronosimplesapi.repository.postgres.reactive;

import dev.tonholo.chronosimplesapi.repository.postgres.entity.IrrfBaseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IrrfBaseEntityReactiveRepository extends ReactiveCrudRepository<IrrfBaseEntity, Integer> {
}
