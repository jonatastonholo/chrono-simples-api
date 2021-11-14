package dev.tonholo.chronosimplesapi.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.exception.ExceptionMessage;
import lombok.Getter;

import java.util.Arrays;

public enum CurrencyCodeType {
    BRL("Real"),
    USD("Dollar");

    @Getter private final String name;

    CurrencyCodeType(String name) {
        this.name = name;
    }

    @JsonCreator
    public static CurrencyCodeType from(String value) {
        try {
            return CurrencyCodeType.valueOf(value);
        } catch (Exception e) {
            throw new ApiException(ExceptionMessage.PROJECT_CURRENCY_CODE_INVALID, Arrays.toString(CurrencyCodeType.values()));
        }
    }
}
