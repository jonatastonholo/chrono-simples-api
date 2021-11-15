package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.repository.postgres.mapper.PeriodMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeriodRepository {
    private final PeriodReactiveRepository periodReactiveRepository;
    private final PeriodMapper periodMapper;

    public Mono<Period> save(Period period) {
        log.debug("Saving period -> {}", period);
        return Mono.just(periodMapper.from(period))
                .flatMap(periodReactiveRepository::save)
                .map(periodMapper::from);
    }

    public Flux<Period> findAll() {
        return periodReactiveRepository
                .findAllNotDeleted()
                .map(periodMapper::from);
    }

    public Mono<Boolean> hasConcurrency(LocalDateTime begin, LocalDateTime end) {
        return findAll()
                .filter(periodSaved
                        -> periodSaved.hasConcurrency(begin, end))
                .hasElements();
    }

    public Mono<Period> findById(String periodId) {
        return periodReactiveRepository
                .findByIdNotDeleted(periodId)
                .map(periodMapper::from);
    }

    public Mono<Period> delete(Period period) {
        return Mono.just(period)
                .map(periodMapper::from)
                .map(periodEntity -> {
                    log.debug("Soft Deleting period -> {}", period);
                    periodEntity.setDeleted(true);
                    periodEntity.setUpdatedAt(LocalDateTime.now());
                    return periodEntity;
                })
                .flatMap(periodReactiveRepository::save)
                .map(periodMapper::from);
    }

    public Mono<Period> findMostRecentPeriodWithoutEnd() {
        return periodReactiveRepository.findMostRecentPeriodWithoutEnd()
                .map(periodMapper::from);
    }
}
