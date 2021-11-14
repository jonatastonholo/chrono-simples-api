package dev.tonholo.chronosimplesapi.repository.postgres.mapper;

import dev.tonholo.chronosimplesapi.domain.Period;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.PeriodEntity;
import org.springframework.stereotype.Service;

@Service
public class PeriodMapper {

    public PeriodEntity from(Period period) {
        return PeriodEntity.builder()
                .id(period.getId())
                .projectId(period.getProjectId())
                .periodBegin(period.getBegin())
                .periodEnd(period.getEnd())
                .hourValue(period.getHourValue())
                .description(period.getDescription())
                .createdAt(period.getCreatedAt())
                .updatedAt(period.getUpdatedAt())
                .build();
    }

    public Period from(PeriodEntity periodEntity) {
        return Period.builder()
                .id(periodEntity.getId())
                .projectId(periodEntity.getProjectId())
                .begin(periodEntity.getPeriodBegin())
                .end(periodEntity.getPeriodEnd())
                .hourValue(periodEntity.getHourValue())
                .description(periodEntity.getDescription())
                .createdAt(periodEntity.getCreatedAt())
                .updatedAt(periodEntity.getUpdatedAt())
                .build();
    }
}
