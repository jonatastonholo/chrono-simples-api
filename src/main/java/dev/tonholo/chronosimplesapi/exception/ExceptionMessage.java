package dev.tonholo.chronosimplesapi.exception;

import lombok.Getter;

public enum ExceptionMessage {

    // General
    INTERNAL_ERROR("Ops... Something went wrong. Try again later."),
    UNAUTHORIZED("Unauthorized"),
    BODY_REQUIRED("The request body is required."),
    REQUEST_CAN_NOT_BE_NULL("The request cannot be null."),

    // Project
    PROJECT_ID_REQUIRED("The project id is required."),
    PROJECT_NOT_FOUND("The project was not found."),
    PROJECT_NAME_REQUIRED("The project name is required."),
    PROJECT_HOUR_VALUE_REQUIRED("The project hour value is required."),
    PROJECT_CURRENCY_CODE_REQUIRED("The project currency code is required."),
    PROJECT_CURRENCY_CODE_INVALID("The project currency code is invalid. Accepted values: {}"),
    PROJECT_ALREADY_EXISTS("There is already a registered project with the given name."),
    PROJECT_UPDATE_AT_LEAST_ONE_PARAMETER_MUST_BE_FILLED("At least one parameter must be filled to update the project."),

    ;





    @Getter
    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

}
