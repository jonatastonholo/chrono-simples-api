package dev.tonholo.chronosimplesapi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PeriodTest {

    @Test
    @DisplayName("Given a same period interval, must have concurrency")
    void hasConcurrency01() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);
        var savedEnd = savedBegin.plusHours(2);

        var period = Period.builder()
                .begin(savedBegin)
                .end(savedEnd)
                .build();

        assertTrue(period.hasConcurrency(savedBegin, savedEnd));
    }

    @Test
    @DisplayName("Given a period interval between saved begin, must have concurrency")
    void hasConcurrency02() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);
        var savedEnd = savedBegin.plusHours(2);

        var period = Period.builder()
                .begin(savedBegin)
                .end(savedEnd)
                .build();

        LocalDateTime begin = savedBegin.plusMinutes(-10);
        LocalDateTime end = savedBegin.plusMinutes(10);

        assertTrue(period.hasConcurrency(begin, end));
    }

    @Test
    @DisplayName("Given a period interval with same begin of saved and with no end, must have concurrency")
    void hasConcurrency03() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);
        var savedEnd = savedBegin.plusHours(2);

        var period = Period.builder()
                .begin(savedBegin)
                .end(savedEnd)
                .build();

        assertTrue(period.hasConcurrency(savedBegin, null));
    }

    @Test
    @DisplayName("Given a period interval initializing after saved begin and with no end, must have concurrency")
    void hasConcurrency04() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);
        var savedEnd = savedBegin.plusHours(2);

        var period = Period.builder()
                .begin(savedBegin)
                .end(savedEnd)
                .build();

        var begin = savedBegin.plusMinutes(22);

        assertTrue(period.hasConcurrency(begin, null));
    }

    @Test
    @DisplayName("Given a period interval initializing after saved begin and with end before saved end, must have concurrency")
    void hasConcurrency05() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);
        var savedEnd = savedBegin.plusHours(2);

        var period = Period.builder()
                .begin(savedBegin)
                .end(savedEnd)
                .build();

        var begin = savedBegin.plusMinutes(40);
        var end = savedEnd.plusMinutes(-20);

        assertTrue(period.hasConcurrency(begin, end));
    }

    @Test
    @DisplayName("Given a period interval initializing at saved begin and with end before saved end, must have concurrency")
    void hasConcurrency06() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);
        var savedEnd = savedBegin.plusHours(2);

        var period = Period.builder()
                .begin(savedBegin)
                .end(savedEnd)
                .build();

        var end = savedEnd.plusSeconds(-1);

        assertTrue(period.hasConcurrency(savedBegin, end));
    }

    @Test
    @DisplayName("Given a period interval initializing after saved begin and with end after saved end, must have concurrency")
    void hasConcurrency07() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);
        var savedEnd = savedBegin.plusHours(2);

        var period = Period.builder()
                .begin(savedBegin)
                .end(savedEnd)
                .build();

        var begin = savedBegin.plusMinutes(20);
        var end = savedEnd.plusSeconds(10);

        assertTrue(period.hasConcurrency(begin, end));
    }

    @Test
    @DisplayName("Given a saved period interval with no end, and given a period initializing at saved begin and with no end, must have concurrency")
    void hasConcurrency08() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);

        var period = Period.builder()
                .begin(savedBegin)
                .end(null)
                .build();

        assertTrue(period.hasConcurrency(savedBegin, null));
    }

    @Test
    @DisplayName("Given a saved period interval with no end, and given a period initializing after saved begin and with no end, must have concurrency")
    void hasConcurrency09() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);

        var period = Period.builder()
                .begin(savedBegin)
                .end(null)
                .build();

        var begin = savedBegin.plusHours(1);

        assertTrue(period.hasConcurrency(begin, null));
    }

    @Test
    @DisplayName("Given a saved period interval with no end, and given a period initializing before saved begin and with no end, must have concurrency")
    void hasConcurrency10() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);

        var period = Period.builder()
                .begin(savedBegin)
                .end(null)
                .build();

        var begin = savedBegin.plusHours(-1);

        assertTrue(period.hasConcurrency(begin, null));
    }

    @Test
    @DisplayName("Given a saved period interval with no end, and given a period initializing between saved begin, must have concurrency")
    void hasConcurrency11() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);

        var period = Period.builder()
                .begin(savedBegin)
                .end(null)
                .build();

        var begin = savedBegin.plusHours(-1);
        var end = savedBegin.plusHours(1);

        assertTrue(period.hasConcurrency(begin, null));
    }

    @Test
    @DisplayName("Given a saved period interval with no end, and given a period initialized and finished after saved begin, must have concurrency")
    void hasConcurrency12() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);

        var period = Period.builder()
                .begin(savedBegin)
                .end(null)
                .build();

        var begin = savedBegin.plusHours(1);
        var end = savedBegin.plusHours(2);

        assertTrue(period.hasConcurrency(begin, null));
    }

    @Test
    @DisplayName("Given a saved period interval with no end, and given a period initialized and finished before saved begin, must not have concurrency")
    void hasConcurrency13() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);

        var period = Period.builder()
                .begin(savedBegin)
                .end(null)
                .build();

        var begin = savedBegin.plusHours(-2);
        var end = savedBegin.plusHours(-1);

        assertFalse(period.hasConcurrency(begin, end));
    }

    @Test
    @DisplayName("Given a saved period interval with saved begin and end, and given a period initialized and finished before saved begin, must not have concurrency")
    void hasConcurrency14() {
        var savedBegin = LocalDateTime.of(2021, 11, 14, 20,29,50, 1234);
        var savedEnd = savedBegin.plusHours(5);

        var period = Period.builder()
                .begin(savedBegin)
                .end(savedEnd)
                .build();

        var begin = savedBegin.plusHours(-2);
        var end = savedBegin.plusHours(-1);

        assertFalse(period.hasConcurrency(begin, end));
    }
}