package dev.tonholo.chronosimplesapi.config;

import dev.tonholo.chronosimplesapi.exception.ApiInternalException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.Objects;

@Configuration
@Getter
public class AppConfig {

    private final BigDecimal deductionAmountPerFinancialDependent;
    private final BigDecimal defaultRFactor;
    private final BigDecimal pensionCeiling;

    @Autowired
    public AppConfig(Environment environment) {
        deductionAmountPerFinancialDependent = BigDecimal.valueOf(Double.parseDouble(environment.getProperty("app.tax.deduction_amount_per_dependent")));
        defaultRFactor = BigDecimal.valueOf(Double.parseDouble(environment.getProperty("app.tax.default_r_factor")));
        pensionCeiling = BigDecimal.valueOf(Double.parseDouble(environment.getProperty("app.tax.pension_ceiling")));
        validate();
    }

    private void validate() {
        if (Objects.isNull(deductionAmountPerFinancialDependent)) {
            throw new ApiInternalException("Application fails to load value of deductionPerFinancialDependent from configuration");
        }
    }
}
