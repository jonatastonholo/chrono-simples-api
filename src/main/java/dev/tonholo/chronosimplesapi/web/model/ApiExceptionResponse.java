package dev.tonholo.chronosimplesapi.web.model;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ApiExceptionResponse {
    int statusCode;
    String message;

    public ApiExceptionResponse(HttpStatus httpStatus, String message) {
        this.statusCode = httpStatus.value();
        this.message = message;
    }

    public ApiExceptionResponse(ApiException apiException) {
        statusCode = apiException.getHttpStatus().value();
        message = apiException.getMessage();
    }
}
