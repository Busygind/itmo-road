package com.busygind;

import com.busygind.trigonometric.Cot;
import com.busygind.trigonometric.Csc;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CscTest extends BaseTest {
    @Mock
    private Sin mockSin;
    @Spy
    private Sin spySin;

    @Test
    void shouldCallSinFunction() {
        Csc csc = new Csc(spySin);
        csc.calculate(ONE, DEFAULT_PRECISION);

        verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void shouldCalcWithMockSin() {
        BigDecimal arg = new BigDecimal(4);

        when(mockSin.calculate(eq(arg), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("-0.75680249"));

        Csc csc = new Csc(mockSin);
        BigDecimal expectedResult = new BigDecimal("-1.3213");
        assertEquals(expectedResult, csc.calculate(arg, DEFAULT_PRECISION));
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
