package dev.tonholo.chronosimplesapi.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, ExceptionMessage.UNAUTHORIZED.getMessage());
    }
}
