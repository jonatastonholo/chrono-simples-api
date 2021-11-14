package dev.tonholo.chronosimplesapi.exception;

import org.springframework.http.HttpStatus;

public class ApiBadGatewayException extends ApiException {

    public ApiBadGatewayException(ExceptionMessage exceptionMessage) {
        super(HttpStatus.BAD_GATEWAY, exceptionMessage.getMessage());
    }
}
