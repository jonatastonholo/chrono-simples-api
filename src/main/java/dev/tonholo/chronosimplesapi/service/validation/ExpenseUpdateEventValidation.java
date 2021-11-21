package dev.tonholo.chronosimplesapi.service.validation;

import dev.tonholo.chronosimplesapi.service.event.ExpenseUpdateEvent;
import dev.tonholo.chronosimplesapi.validator.Validation;
import org.springframework.stereotype.Service;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.*;
import static dev.tonholo.chronosimplesapi.validator.Validator.*;

@Service
public class ExpenseUpdateEventValidation implements Validation<ExpenseUpdateEvent> {

    @Override
    public void validate(ExpenseUpdateEvent expenseUpdateEvent) {
        notNull(expenseUpdateEvent, EVENT_CAN_NOT_BE_NULL);
        notBlank(expenseUpdateEvent.getId(), EXPENSE_ID_REQUIRED);
        notBlank(expenseUpdateEvent.getDescription(), EXPENSE_DESCRIPTION_REQUIRED);
        notNull(expenseUpdateEvent.getValue(), EXPENSE_VALUE_REQUIRED);
        greaterThanZero(expenseUpdateEvent.getValue(), EXPENSE_VALUE_MORE_THAN_ZERO);
        notNull(expenseUpdateEvent.getType(), EXPENSE_TYPE_REQUIRED);
        notNull(expenseUpdateEvent.getPeriodBegin(), EXPENSE_PERIOD_BEGIN_REQUIRED);
        notNull(expenseUpdateEvent.getPeriodEnd(), EXPENSE_PERIOD_END_REQUIRED);
        isTrue(expenseUpdateEvent.getPeriodBegin().isBefore(expenseUpdateEvent.getPeriodEnd()), EXPENSE_PERIOD_BEGIN_MUST_BE_BEFORE_END);
    }
}
