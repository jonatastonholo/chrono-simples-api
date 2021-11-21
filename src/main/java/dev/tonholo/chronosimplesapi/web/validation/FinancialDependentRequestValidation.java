package dev.tonholo.chronosimplesapi.web.validation;

import dev.tonholo.chronosimplesapi.validator.Validation;
import dev.tonholo.chronosimplesapi.web.dto.FinancialDependentRequest;
import org.springframework.stereotype.Service;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.*;
import static dev.tonholo.chronosimplesapi.validator.Validator.*;

@Service
public class FinancialDependentRequestValidation implements Validation<FinancialDependentRequest> {
    @Override
    public void validate(FinancialDependentRequest financialDependentCreationEvent) {
        notNull(financialDependentCreationEvent, REQUEST_CAN_NOT_BE_NULL);
        notBlank(financialDependentCreationEvent.getName(), FINANCIAL_DEPENDENT_NAME_REQUIRED);
        notNull(financialDependentCreationEvent.getIrrfDeduct(), FINANCIAL_DEPENDENT_IRRF_DEDUCT_REQUIRED);
        notNull(financialDependentCreationEvent.getPeriodBegin(), FINANCIAL_DEPENDENT_PERIOD_BEGIN_REQUIRED);
        notNull(financialDependentCreationEvent.getPeriodEnd(), FINANCIAL_DEPENDENT_PERIOD_END_REQUIRED);
        isTrue(financialDependentCreationEvent.getPeriodBegin().isBefore(financialDependentCreationEvent.getPeriodEnd()), FINANCIAL_DEPENDENT_PERIOD_BEGIN_MUST_BE_BEFORE_END);
    }
}
