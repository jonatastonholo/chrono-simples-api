package dev.tonholo.chronosimplesapi.mock.model;

import dev.tonholo.chronosimplesapi.domain.Period;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PeriodMock {

    static Period getPeriodMock (Double hourValue, LocalDateTime begin, int periodHourIncrement) {
        return Period.builder()
                .hourValue(BigDecimal.valueOf(hourValue))
                .begin(begin)
                .end(begin.plusHours(periodHourIncrement))
                .build();
    }
    static Period getPeriodMock (Double hourValue, LocalDateTime begin, int periodHourIncrement, int periodMinuteIncrement, int periodSecondIncrement) {
        return Period.builder()
                .hourValue(BigDecimal.valueOf(hourValue))
                .begin(begin)
                .end(begin.plusHours(periodHourIncrement).plusMinutes(periodMinuteIncrement).plusSeconds(periodSecondIncrement))
                .build();
    }
}
