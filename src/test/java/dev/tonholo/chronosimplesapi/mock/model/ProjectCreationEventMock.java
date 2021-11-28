package dev.tonholo.chronosimplesapi.mock.model;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import dev.tonholo.chronosimplesapi.service.event.ProjectCreationEvent;

import java.math.BigDecimal;

public interface ProjectCreationEventMock {

    static ProjectCreationEvent getProjectCreationEventMock(String name, double hourValue) {
        return ProjectCreationEvent.builder()
                .name(name)
                .hourValue(BigDecimal.valueOf(hourValue))
                .currencyCode(CurrencyCodeType.BRL)
                .build();
    }
}
