package dev.tonholo.chronosimplesapi.mock.model;

import dev.tonholo.chronosimplesapi.domain.InssBase;

import java.math.BigDecimal;
import java.util.List;

public interface InssBaseMock {

    static List<InssBase> getInssBaseMocks() {
        return List.of(
                getIrrfBaseMock(0,1751.81, 0.08),
                getIrrfBaseMock(1751.82,2919.72, 0.09),
                getIrrfBaseMock(2919.73,5839.45, 0.11)
        );
    }

    private static InssBase getIrrfBaseMock(
        double baseValueRangeBegin,
        double baseValueRangeEnd,
        double basePercentage
    ) {
        return InssBase.builder()
                .baseValueRangeBegin(BigDecimal.valueOf(baseValueRangeBegin))
                .baseValueRangeEnd(BigDecimal.valueOf(baseValueRangeEnd))
                .basePercentage(BigDecimal.valueOf(basePercentage))
                .build();
    }

}