package dev.tonholo.chronosimplesapi.repository.postgres.mapper;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.PeriodEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PeriodMapper {

    public PeriodEntity from(Period period) {
        return PeriodEntity.builder()
                .id(period.getId())
                .projectId(period.getProjectId())
                .periodBegin(period.getBegin())
                .periodEnd(period.getEnd())
                .hourValue(period.getHourValue())
                .currency(period.getCurrency())
                .description(period.getDescription())
                .createdAt(period.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Period from(PeriodEntity periodEntity) {
        return Period.builder()
                .id(periodEntity.getId())
                .projectId(periodEntity.getProjectId())
                .begin(periodEntity.getPeriodBegin())
                .end(periodEntity.getPeriodEnd())
                .hourValue(periodEntity.getHourValue())
                .currency(periodEntity.getCurrency())
                .description(periodEntity.getDescription())
                .createdAt(periodEntity.getCreatedAt())
                .updatedAt(periodEntity.getUpdatedAt())
                .build();
    }
}
