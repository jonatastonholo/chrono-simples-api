package dev.tonholo.chronosimplesapi.mock.model;

import dev.tonholo.chronosimplesapi.domain.IrrfBase;

import java.math.BigDecimal;
import java.util.List;

public interface IrrfBaseMock {

    private static IrrfBase getIrrfBaseMock(
        double baseValueRangeBegin,
        double baseValueRangeEnd,
        double aliquot,
        double deduction
    ) {
        return IrrfBase.builder()
                .baseValueRangeBegin(BigDecimal.valueOf(baseValueRangeBegin))
                .baseValueRangeEnd(BigDecimal.valueOf(baseValueRangeEnd))
                .aliquot(BigDecimal.valueOf(aliquot))
                .deduction(BigDecimal.valueOf(deduction))
                .build();
    }

    static List<IrrfBase> getIrrfBaseMocks() {
        return List.of(
          getIrrfBaseMock(0, 1903.98, 0, 0),
          getIrrfBaseMock(1903.99, 2826.65, 0.075, 142.8),
          getIrrfBaseMock(2826.66, 3751.05, 0.15, 354.8),
          getIrrfBaseMock(3751.06, 4664.68, 0.225, 636.13),
          getIrrfBaseMock(4664.69, 100000.00, 0.275, 869.36)
        );
    }
}
