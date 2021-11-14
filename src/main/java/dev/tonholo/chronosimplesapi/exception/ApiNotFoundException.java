package dev.tonholo.chronosimplesapi.exception;

import org.springframework.http.HttpStatus;

public class ApiNotFoundException extends ApiException {
    public ApiNotFoundException(ExceptionMessage exceptionMessage) {
        super(HttpStatus.NOT_FOUND, exceptionMessage.getMessage());
    }
}
