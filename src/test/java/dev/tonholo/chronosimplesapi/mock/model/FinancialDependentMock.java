package dev.tonholo.chronosimplesapi.mock.model;

import dev.tonholo.chronosimplesapi.domain.FinancialDependent;

public interface FinancialDependentMock {

    static FinancialDependent getFinancialDependentMock(String name, boolean isIrrfDeduct) {
        return FinancialDependent.builder()
                .name(name)
                .irrfDeduct(isIrrfDeduct)
                .build();
    }
}
