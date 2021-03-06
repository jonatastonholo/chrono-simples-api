package dev.tonholo.chronosimplesapi.domain;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.math.RoundingMode.HALF_UP;

@Data
@Builder
public class Period {
    private String id;
    private String projectId;
    private Project project;
    private BigDecimal hourValue;
    private CurrencyCodeType currency;
    private LocalDateTime begin;
    private LocalDateTime end;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean hasConcurrency(LocalDateTime beginToCompare, LocalDateTime endToCompare) {
        final var beginIsBetweenSavedPeriod
                = !beginToCompare.isBefore(begin)
                && end!= null
                && !beginToCompare.isAfter(end);

        final var endIsNotNullAndBetweenSavedPeriod
                = endToCompare != null
                && endToCompare.isAfter(begin)
                && end!= null
                && !endToCompare.isAfter(end);

        final var savedPeriodNotOverYet = end == null;

        final var savedPeriodNotOverYetAndBeginIsAfterOrEqualSavedBegin
                = savedPeriodNotOverYet
                && !beginToCompare.isBefore(begin);

        final var savedPeriodNotOverYetAndEndIsNull
                = savedPeriodNotOverYet
                && endToCompare == null;

        final var savedPeriodNotOverYetAndEndIsAfterSavedBegin
                = savedPeriodNotOverYet
                && endToCompare != null
                && endToCompare.isAfter(begin);

        return beginIsBetweenSavedPeriod
                || endIsNotNullAndBetweenSavedPeriod
                || savedPeriodNotOverYetAndBeginIsAfterOrEqualSavedBegin
                || savedPeriodNotOverYetAndEndIsNull
                || savedPeriodNotOverYetAndEndIsAfterSavedBegin;
    }

    public Period completeFrom(Period source) {
        id = (id == null) ? source.id : id;
        projectId = (projectId == null) ? source.projectId : projectId;
        hourValue = (hourValue == null) ? source.hourValue : hourValue;
        currency = (currency == null) ? source.currency : currency;
        begin = (begin == null) ? source.begin : begin;
        end = (end == null) ? source.end : end;
        description = (description == null) ? source.description : description;
        createdAt = (createdAt == null) ? source.createdAt : createdAt;
        updatedAt = (updatedAt == null) ? source.updatedAt : updatedAt;
        return this;
    }

    public BigDecimal getAmount() {
        final BigDecimal secondValue = hourValue.divide(BigDecimal.valueOf(3600), 8, HALF_UP);
        final long periodDurationSeconds = Duration.between(begin, end)
                .toSeconds();
        return secondValue
                .multiply(BigDecimal.valueOf(periodDurationSeconds))
                .setScale(2, HALF_UP);
    }
}
