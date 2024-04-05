package com.busygind;

import com.busygind.trigonometric.Cos;
import com.busygind.trigonometric.Cot;
import com.busygind.trigonometric.Sin;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CotTest extends BaseTest {
    @Mock
    private Sin mockSin;
    @Mock
    private Cos mockCos;
    @Spy
    private Sin spySin;

    @Test
    void shouldCallSinAndCosFunctions() {
        Cos cos = new Cos(spySin);
        Cos spyCos = spy(cos);

        Cot cot = new Cot(spySin, spyCos);
        cot.calculate(ONE, DEFAULT_PRECISION);

        verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void shouldCalculateWithMockSinAndMockCos() {
        final BigDecimal arg = new BigDecimal(4);

        when(mockSin.calculate(eq(arg), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("-0.75680249"));
        when(mockCos.calculate(eq(arg), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("-0.65364362"));

        Cot cot = new Cot(mockSin, mockCos);
        BigDecimal expectedResult = new BigDecimal("0.8637");
        assertEquals(expectedResult, cot.calculate(arg, DEFAULT_PRECISION));
    }

    @Test
    void shouldNotCalculateForZero() {
        Cot cot = new Cot();
        assertThrows(ArithmeticException.class, () -> cot.calculate(ZERO, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForPiDividedByTwo() {
        Cot cot = new Cot();
        assertEquals(
                ZERO.setScale(DEFAULT_PRECISION.scale(), HALF_EVEN),
                cot.calculate(HALF_PI, DEFAULT_PRECISION)
        );
    }
}
