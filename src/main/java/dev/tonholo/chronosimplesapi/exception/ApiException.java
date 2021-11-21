package dev.tonholo.chronosimplesapi.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final int httpStatus;
    private final String message;

    public ApiException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        httpStatus = 400;
        message = exceptionMessage.getMessage();
    }

    public ApiException(ExceptionMessage exceptionMessage, String... args) {
        super(getErrorMessageWithArgs(exceptionMessage.getMessage(), args));
        httpStatus = 400;
        message = getErrorMessageWithArgs(exceptionMessage.getMessage(), args);
    }

    protected ApiException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private static String getErrorMessageWithArgs(String message, String ... values) {
        String errorDescription = message;
        for (String value : values) {
            errorDescription = errorDescription.replaceFirst("\\{}", value);
        }
        return errorDescription;
    }
}
