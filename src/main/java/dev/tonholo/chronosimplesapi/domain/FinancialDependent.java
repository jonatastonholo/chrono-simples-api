package dev.tonholo.chronosimplesapi.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class FinancialDependent {
    private String id;
    private String name;
    private Boolean irrfDeduct;
    private LocalDate periodBegin;
    private LocalDate periodEnd;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;

    public FinancialDependent completeFrom(FinancialDependent source) {
        id = (id == null) ? source.id : id;
        name = (name == null) ? source.name : name;
        irrfDeduct = (irrfDeduct == null) ? source.irrfDeduct : irrfDeduct;
        periodBegin = (periodBegin == null) ? source.periodBegin : periodBegin;
        periodEnd = (periodEnd == null) ? source.periodEnd : periodEnd;
        createdAt = (createdAt == null) ? source.createdAt : createdAt;
        updatedAt = (updatedAt == null) ? source.updatedAt : updatedAt;

        return this;
    }
}
