package com.busygind;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.stream.Stream;

import com.busygind.function.FunctionsSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FunctionsSystemTest {
    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.00000001");
    private final FunctionsSystem system = new FunctionsSystem();

    @Test
    void calcForPositiveValueShouldOK() {
        BigDecimal expected = new BigDecimal("-851.85371995");
        assertEquals(expected, system.calculate(new BigDecimal("0.5"), DEFAULT_PRECISION));
    }

    @Test
    void calcForNegativeValueShouldOK() {
        BigDecimal expected = new BigDecimal("-0.05666412");
        assertEquals(expected, system.calculate(new BigDecimal(-1), DEFAULT_PRECISION));
    }

    @Test
    void calcWithNullPrecisionShouldThrow() {
        assertThrows(NullPointerException.class, () -> system.calculate(new BigDecimal(-2), null));
    }

    @ParameterizedTest
    @MethodSource("incorrectPrecisions")
    void calcWithIncorrectPrecisionsShouldThrow(BigDecimal precision) {
        assertThrows(ArithmeticException.class, () -> system.calculate(new BigDecimal(-2), precision));
    }

    @Test
    void calcForZeroShouldThrow() {
        assertThrows(ArithmeticException.class, () -> system.calculate(ZERO, DEFAULT_PRECISION));
    }

    @Test
    void calcWithOneArgumentShouldReturnNull() {
        assertNull(system.calculate(ONE, DEFAULT_PRECISION));
    }

    private static Stream<Arguments> incorrectPrecisions() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(1)),
                Arguments.of(BigDecimal.valueOf(0)),
                Arguments.of(BigDecimal.valueOf(1.01)),
                Arguments.of(BigDecimal.valueOf(-0.01)));
    }
}
