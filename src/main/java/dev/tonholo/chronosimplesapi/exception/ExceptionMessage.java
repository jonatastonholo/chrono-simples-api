package dev.tonholo.chronosimplesapi.exception;

import lombok.Getter;

public enum ExceptionMessage {

    // General
    INTERNAL_ERROR("Ops... Something went wrong. Try again later."),
    UNAUTHORIZED("Unauthorized."),
    BODY_REQUIRED("The request body is required."),
    REQUEST_CAN_NOT_BE_NULL("The request cannot be null."),
    EVENT_CAN_NOT_BE_NULL("The event cannot be null."),

    // Project
    PROJECT_ID_REQUIRED("The project id is required."),
    PROJECT_NOT_FOUND("The project was not found."),
    PROJECT_NAME_REQUIRED("The project name is required."),
    PROJECT_HOUR_VALUE_REQUIRED("The project hour value is required."),
    PROJECT_CURRENCY_CODE_REQUIRED("The project currency code is required."),
    PROJECT_CURRENCY_CODE_INVALID("The project currency code is invalid. Accepted values: {}."),
    PROJECT_ALREADY_EXISTS("There is already a registered project with the given name."),
    PROJECT_UPDATE_AT_LEAST_ONE_PARAMETER_MUST_BE_FILLED("At least one parameter must be filled to update the project."),

    // Period
    PERIOD_NOT_FOUND("The period was not found."),
    PERIOD_BEGIN_REQUIRED("The period begin is required."),
    PERIOD_IS_CONCOMITANT("The period informed has concurrency."),
    PERIOD_BEGIN_MUST_BE_BEFORE_END("The period begin must be before the period end."),
    PERIOD_ID_REQUIRED("The period id is required."),
    STOPWATCH_NOT_RUNNING_TO_STOP("The stopwatch is not running to be stopped."),
    STOPWATCH_NOT_RUNNING_TO_LISTEN("The stopwatch is not running to be listened."),

    // Expense
    EXPENSE_NOT_FOUND("The expense was not found."),
    EXPENSE_ID_REQUIRED("The expense id is required."),
    EXPENSE_TYPE_INVALID("The expense type is invalid. Accepted values: {}."),
    EXPENSE_DESCRIPTION_REQUIRED("The expense description is required."),
    EXPENSE_VALUE_REQUIRED("The expense value is required."),
    EXPENSE_VALUE_MORE_THAN_ZERO("The expense value must be more than zero."),
    EXPENSE_TYPE_REQUIRED("The expense type is required."),
    EXPENSE_PERIOD_BEGIN_REQUIRED("The expense period begin is required."),
    EXPENSE_PERIOD_END_REQUIRED("The expense period end is required."),
    EXPENSE_PERIOD_BEGIN_MUST_BE_BEFORE_END("The expense period begin must be before the period end."),
    ;

    @Getter
    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

}
