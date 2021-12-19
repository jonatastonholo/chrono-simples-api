package dev.tonholo.chronosimplesapi.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.exception.ExceptionMessage;
import lombok.Getter;

import java.util.Arrays;

public enum ExpenseType {
    PERSONAL("Gastos dedutíveis no IRRF"),
    COMPANY("Gastos da empresa");

    @Getter
    private String description;

    ExpenseType(String description) {
        this.description = description;
    }

    @JsonCreator
    public static ExpenseType from(String value) {
        try {
            return ExpenseType.valueOf(value);
        } catch (Exception e) {
            throw new ApiException(ExceptionMessage.EXPENSE_TYPE_INVALID, Arrays.toString(ExpenseType.values()));
        }
    }

    public boolean isPersonal() {
        return PERSONAL.equals(this);
    }

    public boolean isCompany() {
        return COMPANY.equals(this);
    }
}
