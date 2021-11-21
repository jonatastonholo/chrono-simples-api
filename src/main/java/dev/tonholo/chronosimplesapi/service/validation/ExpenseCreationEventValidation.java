package dev.tonholo.chronosimplesapi.service.validation;

import dev.tonholo.chronosimplesapi.service.event.ExpenseCreationEvent;
import dev.tonholo.chronosimplesapi.validator.Validation;
import org.springframework.stereotype.Service;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.*;
import static dev.tonholo.chronosimplesapi.validator.Validator.*;

@Service
public class ExpenseCreationEventValidation implements Validation<ExpenseCreationEvent> {
    @Override
    public void validate(ExpenseCreationEvent expenseCreationEvent) {
        notNull(expenseCreationEvent, EVENT_CAN_NOT_BE_NULL);
        notBlank(expenseCreationEvent.getDescription(), EXPENSE_DESCRIPTION_REQUIRED);
        notNull(expenseCreationEvent.getValue(), EXPENSE_VALUE_REQUIRED);
        greaterThanZero(expenseCreationEvent.getValue(), EXPENSE_VALUE_MORE_THAN_ZERO);
        notNull(expenseCreationEvent.getType(), EXPENSE_TYPE_REQUIRED);
        notNull(expenseCreationEvent.getPeriodBegin(), EXPENSE_PERIOD_BEGIN_REQUIRED);
        notNull(expenseCreationEvent.getPeriodEnd(), EXPENSE_PERIOD_END_REQUIRED);
        isTrue(expenseCreationEvent.getPeriodBegin().isBefore(expenseCreationEvent.getPeriodEnd()), EXPENSE_PERIOD_BEGIN_MUST_BE_BEFORE_END);
    }
}
