package dev.tonholo.chronosimplesapi.repository.postgres;

import dev.tonholo.chronosimplesapi.domain.FinancialDependent;
import dev.tonholo.chronosimplesapi.repository.postgres.mapper.FinancialDependentMapper;
import dev.tonholo.chronosimplesapi.repository.postgres.reactive.FinancialDependentReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinancialDependentRepository {
    private final FinancialDependentReactiveRepository financialDependentReactiveRepository;
    private final FinancialDependentMapper financialDependentMapper;
    

    public Mono<FinancialDependent> save(FinancialDependent financialDependent) {
        log.debug("Saving financial dependent -> {}", financialDependent);
        return Mono.just(financialDependentMapper.from(financialDependent))
                .flatMap(financialDependentReactiveRepository::save)
                .map(financialDependentMapper::from);
    }

    public Mono<FinancialDependent> findById(String financialDependentId) {
        return financialDependentReactiveRepository
                .findByIdNotDeleted(financialDependentId)
                .map(financialDependentMapper::from);
    }

    public Flux<FinancialDependent> findAll() {
        return financialDependentReactiveRepository
                .findAllNotDeleted()
                .map(financialDependentMapper::from);
    }

    public Mono<FinancialDependent> delete(FinancialDependent financialDependent) {
        return Mono.just(financialDependent)
                .map(financialDependentMapper::from)
                .map(financialDependentEntity -> {
                    log.debug("Soft Deleting financial dependent -> {}", financialDependent);
                    financialDependentEntity.setDeleted(true);
                    financialDependentEntity.setUpdatedAt(LocalDateTime.now());
                    return financialDependentEntity;
                })
                .flatMap(financialDependentReactiveRepository::save)
                .map(financialDependentMapper::from);
    }

    public Mono<Integer> countByPeriodRange(LocalDate periodBegin, LocalDate periodEnd) {
        return financialDependentReactiveRepository.countByPeriodRange(periodBegin, periodEnd);
    }

    public Flux<FinancialDependent> findByPeriodRange(LocalDate periodBegin, LocalDate periodEnd) {
        return financialDependentReactiveRepository
                .findByPeriodRange(periodBegin, periodEnd)
                .map(financialDependentMapper::from);
    }
}
