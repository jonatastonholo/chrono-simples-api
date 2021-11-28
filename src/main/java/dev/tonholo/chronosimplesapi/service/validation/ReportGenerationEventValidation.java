package dev.tonholo.chronosimplesapi.service.validation;

import dev.tonholo.chronosimplesapi.service.event.ReportGenerationEvent;
import dev.tonholo.chronosimplesapi.validator.Validation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.*;
import static dev.tonholo.chronosimplesapi.validator.Validator.*;

@Service
public class ReportGenerationEventValidation implements Validation<ReportGenerationEvent> {

    @Override
    public void validate(ReportGenerationEvent reportGenerationEvent) {
        notNull(reportGenerationEvent, EVENT_CAN_NOT_BE_NULL);
        notNull(reportGenerationEvent.getPeriodBegin(), TAX_CALCULATION_PERIOD_BEGIN_REQUIRED);
        notNull(reportGenerationEvent.getPeriodEnd(), TAX_CALCULATION_PERIOD_END_REQUIRED);
        isTrue(reportGenerationEvent.getPeriodBegin().isBefore(reportGenerationEvent.getPeriodEnd()), TAX_CALCULATION_PERIOD_BEGIN_MUST_BE_BEFORE_END);

        reportGenerationEvent.getRFactor().ifPresent(rFactor -> {
            greaterThanOrEqual(rFactor, BigDecimal.valueOf(0.28), TAX_CALCULATION_R_FACTOR_BIGGER_THAN_28_PERCENT);
            lessThan(rFactor, BigDecimal.valueOf(1), TAX_CALCULATION_R_FACTOR_LESS_THAN_100_PERCENT);
        });
    }
}
