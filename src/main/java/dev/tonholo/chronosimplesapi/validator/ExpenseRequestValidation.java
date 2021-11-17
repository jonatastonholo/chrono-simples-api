package dev.tonholo.chronosimplesapi.validator;

import dev.tonholo.chronosimplesapi.web.model.ExpenseRequest;
import org.springframework.stereotype.Service;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.*;
import static dev.tonholo.chronosimplesapi.validator.Validator.*;

@Service
public class ExpenseRequestValidation implements Validation<ExpenseRequest> {
    @Override
    public void validate(ExpenseRequest expenseRequest) {
        notNull(expenseRequest, REQUEST_CAN_NOT_BE_NULL);
        notBlank(expenseRequest.getDescription(), EXPENSE_DESCRIPTION_REQUIRED);
        notNull(expenseRequest.getValue(), EXPENSE_VALUE_REQUIRED);
        greaterThanZero(expenseRequest.getValue(), EXPENSE_VALUE_MORE_THAN_ZERO);
        notNull(expenseRequest.getType(), EXPENSE_TYPE_REQUIRED);
        notNull(expenseRequest.getPeriodBegin(), EXPENSE_PERIOD_BEGIN_REQUIRED);
        notNull(expenseRequest.getPeriodEnd(), EXPENSE_PERIOD_END_REQUIRED);
        isTrue(expenseRequest.getPeriodBegin().isBefore(expenseRequest.getPeriodEnd()), EXPENSE_PERIOD_BEGIN_MUST_BE_BEFORE_END);
    }
}
