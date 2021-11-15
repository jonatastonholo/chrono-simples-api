package dev.tonholo.chronosimplesapi.domain;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Project {
    private String id;
    private String name;
    private BigDecimal hourValue;
    private CurrencyCodeType currencyCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Project completeFrom(Project source) {
        id = (id == null) ? source.id : id;
        name = (name == null) ? source.name : name;
        hourValue = (hourValue == null) ? source.hourValue : hourValue;
        currencyCode = (currencyCode == null) ? source.currencyCode : currencyCode;
        createdAt = (createdAt == null) ? source.createdAt : createdAt;
        updatedAt = (updatedAt == null) ? source.updatedAt : updatedAt;
        return this;
    }
}
