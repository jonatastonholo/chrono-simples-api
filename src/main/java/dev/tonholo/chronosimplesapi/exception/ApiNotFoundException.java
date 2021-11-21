package dev.tonholo.chronosimplesapi.exception;

public class ApiNotFoundException extends ApiException {
    public ApiNotFoundException(ExceptionMessage exceptionMessage) {
        super(404, exceptionMessage.getMessage());
    }
}
