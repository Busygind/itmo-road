package com.busygind;

import com.busygind.trigonometric.Cos;
import com.busygind.trigonometric.Sin;
import com.busygind.trigonometric.Tan;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TanTest extends BaseTest {
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

        Tan tan = new Tan(spySin, spyCos);
        tan.calculate(ZERO, DEFAULT_PRECISION);

        verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void shouldCalcWithMockSinAndMockCos() {
        BigDecimal arg = new BigDecimal(4);

        when(mockSin.calculate(eq(arg), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("-0.75680249"));
        when(mockCos.calculate(eq(arg), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("-0.65364362"));

        Tan tan = new Tan(mockSin, mockCos);
        BigDecimal expectedResult = new BigDecimal("1.1578");
        assertEquals(expectedResult, tan.calculate(arg, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalcForZero() {
        Tan tan = new Tan();
        assertEquals(
                ZERO.setScale(DEFAULT_PRECISION.scale(), HALF_EVEN),
                tan.calculate(ZERO, DEFAULT_PRECISION)
        );
    }

    @Test
    void shouldNotCalcForPiDividedByTwo() {
        Tan tan = new Tan();
        assertThrows(ArithmeticException.class, () -> tan.calculate(HALF_PI, DEFAULT_PRECISION));
    }
}
