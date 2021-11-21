package dev.tonholo.chronosimplesapi.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiInternalException extends ApiException {

    public ApiInternalException(String logMessage, Object ... args) {
        super(500, ExceptionMessage.INTERNAL_ERROR.getMessage());
        log.error(logMessage, args, this);
    }
}
