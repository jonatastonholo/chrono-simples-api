package dev.tonholo.chronosimplesapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class ApiInternalException extends ApiException {

    public ApiInternalException(String logMessage, Object ... args) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessage.INTERNAL_ERROR.getMessage());
        log.error(logMessage, args, this);
    }
}
