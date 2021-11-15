package dev.tonholo.chronosimplesapi.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import dev.tonholo.chronosimplesapi.web.model.ApiExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.CodecException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.INTERNAL_ERROR;
import static org.springframework.http.HttpStatus.*;

@Component
@Order(-2)
@Slf4j
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  WebProperties.Resources resources,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions
                .route(RequestPredicates.all(), request -> formatErrorResponse(request, errorAttributes));
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request,ErrorAttributes errorAttributes) {
        final ApiExceptionResponse response;
        try {
            final var error = errorAttributes.getError(request);
            if (Objects.isNull(error)) {
                log.error("An internal error occurred in which it was not possible to perform the stack trace");
                response = new ApiExceptionResponse(INTERNAL_SERVER_ERROR, INTERNAL_ERROR.getMessage());
            } else {
                if (error instanceof ApiException) {
                    final var apiException = (ApiException) error;
                    log.error(apiException.getMessage(), error);
                    response = new ApiExceptionResponse(apiException);
                } else if (error instanceof UnsupportedOperationException
                            || (error instanceof ResponseStatusException && HttpStatus.NOT_FOUND.equals(((ResponseStatusException) error).getStatus()))) {
                    log.error("Endpoint not implemented", error);
                    response = new ApiExceptionResponse(NOT_IMPLEMENTED, "Endpoint not implemented.");
                } else {
                    final var exception = ((ServerWebInputException) error).getMostSpecificCause();
                    if (exception instanceof ApiException) {
                        final var apiException = (ApiException) exception;
                        log.error(apiException.getMessage(), exception);
                        response = new ApiExceptionResponse(apiException);

                    } else if(exception instanceof UnsupportedOperationException
                            || (exception instanceof ResponseStatusException && HttpStatus.NOT_FOUND.equals(((ResponseStatusException) exception).getStatus()))) {
                        log.error("Endpoint not implemented", exception);
                        response = new ApiExceptionResponse(NOT_IMPLEMENTED, "Endpoint not implemented.");
                    } else {
                        if (exception instanceof JsonMappingException || exception instanceof JsonParseException) {
                            log.error("Error trying to serialize/deserialize JSON. Payload contains syntax error.", exception);
                            response = new ApiExceptionResponse(BAD_REQUEST, "Error trying to serialize/deserialize JSON. Payload contains syntax error.");
                        } else if(exception instanceof CodecException) {
                                log.error("Any decoding error occurred.", exception);
                                response = new ApiExceptionResponse(INTERNAL_SERVER_ERROR, INTERNAL_ERROR.getMessage());
                        } else {
                            log.error("[Internal error] Cause: {} | message: {}", exception.getCause(), exception.getMessage(), exception);
                            response = new ApiExceptionResponse(INTERNAL_SERVER_ERROR, INTERNAL_ERROR.getMessage());
                        }
                    }
                }
            }

            return ServerResponse
                    .status(response.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(response);

        } catch (Exception e) {
            log.error("An error occurred handling global exceptions.");
            return ServerResponse
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new ApiExceptionResponse(INTERNAL_SERVER_ERROR, INTERNAL_ERROR.getMessage()));
        }
    }
    /*
    private Mono<ServerResponse> formatErrorResponse(ServerRequest request,ErrorAttributes errorAttributes) {
        final var exception = errorAttributes.getError(request);
        final ApiExceptionResponse response;
        if (Objects.isNull(exception)) {
            log.error("An internal error occurred in which it was not possible to perform the stack trace");
            response = new ApiExceptionResponse(INTERNAL_SERVER_ERROR, INTERNAL_ERROR.getMessage());
        } else if (exception instanceof ApiException
                || ((ServerWebInputException) exception).getMostSpecificCause() instanceof ApiException) {
            ApiException apiException;
            try {
                apiException = (ApiException) exception;
            } catch (Exception ignored) {
                apiException = (ApiException) (((ServerWebInputException) exception).getMostSpecificCause());
            }
            log.error(apiException.getMessage(), exception);
            response = new ApiExceptionResponse(apiException);
        } else if(exception instanceof UnsupportedOperationException
                    || (exception instanceof ResponseStatusException && HttpStatus.NOT_FOUND.equals(((ResponseStatusException) exception).getStatus()))) {
            log.error("Endpoint not implemented", exception);
            response = new ApiExceptionResponse(NOT_IMPLEMENTED, "Endpoint not implemented.");
        } else {
            if(exception.getCause() instanceof DecodingException
                    || exception.getCause() instanceof CodecException) {
                if (exception.getCause().getCause() instanceof JsonMappingException
                        || exception.getCause().getCause() instanceof JsonParseException) {
                    log.error("Error trying to deserialize JSON. Payload contains syntax error.", exception);
                } else {
                    log.error("Any decoding error occurred.", exception);
                }
            } else {
                log.error("[Internal error] Cause: {} | message: {}", exception.getCause(), exception.getMessage(), exception);
            }
            response = new ApiExceptionResponse(INTERNAL_SERVER_ERROR, INTERNAL_ERROR.getMessage());
        }

        return ServerResponse
                .status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response);
    }

     */

}
