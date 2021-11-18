package dev.tonholo.chronosimplesapi.validator;

import dev.tonholo.chronosimplesapi.domain.event.FinancialDependentUpdateEvent;
import org.springframework.stereotype.Service;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.*;
import static dev.tonholo.chronosimplesapi.validator.Validator.*;

@Service
public class FinancialDependentUpdateEventValidation implements Validation<FinancialDependentUpdateEvent> {

    @Override
    public void validate(FinancialDependentUpdateEvent financialDependentUpdateEvent) {
        notNull(financialDependentUpdateEvent, EVENT_CAN_NOT_BE_NULL);
        notBlank(financialDependentUpdateEvent.getId(), EXPENSE_ID_REQUIRED);
        notBlank(financialDependentUpdateEvent.getName(), FINANCIAL_DEPENDENT_NAME_REQUIRED);
        notNull(financialDependentUpdateEvent.getIrrfDeduct(), FINANCIAL_DEPENDENT_IRRF_DEDUCT_REQUIRED);
        notNull(financialDependentUpdateEvent.getPeriodBegin(), FINANCIAL_DEPENDENT_PERIOD_BEGIN_REQUIRED);
        notNull(financialDependentUpdateEvent.getPeriodEnd(), FINANCIAL_DEPENDENT_PERIOD_END_REQUIRED);
        isTrue(financialDependentUpdateEvent.getPeriodBegin().isBefore(financialDependentUpdateEvent.getPeriodEnd()), FINANCIAL_DEPENDENT_PERIOD_BEGIN_MUST_BE_BEFORE_END);
    }
}
