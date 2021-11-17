package dev.tonholo.chronosimplesapi.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.exception.ExceptionMessage;

import java.util.Arrays;

public enum ExpenseType {
    PERSONAL,
    COMPANY;

    @JsonCreator
    public static ExpenseType from(String value) {
        try {
            return ExpenseType.valueOf(value);
        } catch (Exception e) {
            throw new ApiException(ExceptionMessage.EXPENSE_TYPE_INVALID, Arrays.toString(ExpenseType.values()));
        }
    }
}
