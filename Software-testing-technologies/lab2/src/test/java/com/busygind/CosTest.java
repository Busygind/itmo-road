package com.busygind;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;

import com.busygind.trigonometric.Cos;
import com.busygind.trigonometric.Sin;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

class CosTest extends BaseTest {
    @Mock
    private Sin mockSin;
    @Spy
    private Sin spySin;

    @Test
    void shouldCallSinFunction() {
        Cos cos = new Cos(spySin);
        cos.calculate(new BigDecimal(6), new BigDecimal("0.001"));

        verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void shouldCalculateWithMockSin() {
        BigDecimal arg = new BigDecimal(5);
        MathContext mc = new MathContext(DECIMAL128.getPrecision());
        BigDecimal correctedArg =
                BigDecimalMath.pi(mc)
                        .divide(new BigDecimal(2), DECIMAL128.getPrecision(), HALF_EVEN)
                        .subtract(arg);
        BigDecimal sinResult = new BigDecimal("0.283662");

        when(mockSin.calculate(eq(correctedArg), any(BigDecimal.class))).thenReturn(sinResult);

        Cos cos = new Cos(mockSin);

        assertEquals(sinResult, cos.calculate(arg, new BigDecimal("0.000001")));
    }

    @Test
    void shouldCalculateForZero() {
        Cos cos = new Cos();
        assertEquals(ONE, cos.calculate(ZERO, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForPiDividedByTwo() {
        final Cos cos = new Cos();
        assertEquals(
                ZERO.setScale(DEFAULT_PRECISION.scale(), HALF_EVEN),
                cos.calculate(HALF_PI, DEFAULT_PRECISION)
        );
    }

    @Test
    void shouldCalculateForOne() {
        final Cos cos = new Cos();
        final BigDecimal expected = new BigDecimal("0.5403");
        assertEquals(expected, cos.calculate(ONE, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForPeriodic() {
        final Cos cos = new Cos();
        final BigDecimal expected = new BigDecimal("-0.8797");
        assertEquals(expected, cos.calculate(new BigDecimal(-543), DEFAULT_PRECISION));
    }
}
