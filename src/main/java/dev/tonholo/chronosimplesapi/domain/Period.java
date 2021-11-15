package dev.tonholo.chronosimplesapi.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Period {
    private String id;
    private String projectId;
    private BigDecimal hourValue;
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
}
