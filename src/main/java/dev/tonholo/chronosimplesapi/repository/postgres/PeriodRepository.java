package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.repository.postgres.mapper.PeriodMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

    public Mono<Boolean> hasConcurrency(Period period) {
        return periodReactiveRepository.hasConcurrency(period.getBegin())
                .map(count ->  (count > 0));
    }
}
