package dev.tonholo.chronosimplesapi.exception;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException() {
        super(401, ExceptionMessage.UNAUTHORIZED.getMessage());
    }
}
