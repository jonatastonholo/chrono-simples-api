package dev.tonholo.chronosimplesapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public ApiException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        httpStatus = HttpStatus.BAD_REQUEST;
        message = exceptionMessage.getMessage();
    }

    public ApiException(ExceptionMessage exceptionMessage, String... args) {
        super(getErrorMessageWithArgs(exceptionMessage.getMessage(), args));
        httpStatus = HttpStatus.BAD_REQUEST;
        message = getErrorMessageWithArgs(exceptionMessage.getMessage(), args);
    }

    protected ApiException(HttpStatus httpStatus, String message) {
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
