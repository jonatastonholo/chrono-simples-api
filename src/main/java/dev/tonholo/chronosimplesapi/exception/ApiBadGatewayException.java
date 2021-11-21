package dev.tonholo.chronosimplesapi.exception;

public class ApiBadGatewayException extends ApiException {

    public ApiBadGatewayException(ExceptionMessage exceptionMessage) {
        super(502, exceptionMessage.getMessage());
    }
}
