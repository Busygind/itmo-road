package com.busygind;

import com.busygind.trigonometric.Sin;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SinTest extends BaseTest {
    @Test
    void shouldCalculateForZero() {
        final Sin sin = new Sin();
        assertEquals(ZERO.setScale(4, HALF_EVEN), sin.calculate(ZERO, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForPiDividedByTwo() {
        Sin sin = new Sin();
        assertEquals(
                ONE.setScale(DEFAULT_PRECISION.scale(), HALF_EVEN),
                sin.calculate(HALF_PI, DEFAULT_PRECISION)
        );
    }

    @Test
    void shouldCalculateForOne() {
        final Sin sin = new Sin();
        final BigDecimal expected = new BigDecimal("0.8415");
        assertEquals(expected, sin.calculate(ONE, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForPeriodic() {
        final Sin sin = new Sin();
        final BigDecimal expected = new BigDecimal("0.0972");
        assertEquals(expected, sin.calculate(new BigDecimal(-113), DEFAULT_PRECISION));
    }
}
