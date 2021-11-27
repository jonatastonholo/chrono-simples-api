package dev.tonholo.chronosimplesapi.validator;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.exception.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
public abstract class Validator {
    private Validator() {}

    public static void notNull(Object object, ExceptionMessage exceptionMessage) {
        if (Objects.isNull(object)) {
            throw new ApiException(exceptionMessage);
        }
    }

    public static void isNull(Object object, ExceptionMessage exceptionMessage) {
        if (!Objects.isNull(object)) throwValidationException(exceptionMessage);
    }

    public static void notBlank(String str, ExceptionMessage exceptionMessage) {
        if (Strings.isBlank(str)) throwValidationException(exceptionMessage);
    }

    public static void isTrue(boolean value, ExceptionMessage exceptionMessage) {
        if (!value) throwValidationException(exceptionMessage);
    }

    public static void isFalse(boolean value, ExceptionMessage exceptionMessage) {
        if (value) throwValidationException(exceptionMessage);
    }

    public static void greaterThanZero(BigDecimal value, ExceptionMessage exceptionMessage) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) throwValidationException(exceptionMessage);
    }

    public static void greaterThanOrEqual(BigDecimal value, BigDecimal greaterThanOrEqual, ExceptionMessage exceptionMessage) {
        if (greaterThanOrEqual.compareTo(value) < 0) throwValidationException(exceptionMessage);
    }

    public static void lessThan(BigDecimal value, BigDecimal lessThan, ExceptionMessage exceptionMessage) {
        if (lessThan.compareTo(value) >= 0) throwValidationException(exceptionMessage);
    }

    private static void throwValidationException(ExceptionMessage exceptionMessage) {
        log.error("Validation Error: {}", exceptionMessage.getMessage());
        throw new ApiException(exceptionMessage);
    }
}
