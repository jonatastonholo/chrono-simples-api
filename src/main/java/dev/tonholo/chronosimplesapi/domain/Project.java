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

    public Project merge(Project projectSaved) {
        id = (id == null) ? projectSaved.id : id;
        name = (name == null) ? projectSaved.name : name;
        hourValue = (hourValue == null) ? projectSaved.hourValue : hourValue;
        currencyCode = (currencyCode == null) ? projectSaved.currencyCode : currencyCode;
        createdAt = (createdAt == null) ? projectSaved.createdAt : createdAt;
        updatedAt = (updatedAt == null) ? projectSaved.updatedAt : updatedAt;
        return this;
    }
}
