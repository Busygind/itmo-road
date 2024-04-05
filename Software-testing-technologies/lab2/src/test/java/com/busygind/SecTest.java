package com.busygind;

import com.busygind.trigonometric.Cos;
import com.busygind.trigonometric.Sec;
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

public class SecTest extends BaseTest {
    @Mock
    private Cos mockCos;
    @Spy
    private Cos spyCos;

    @Test
    void shouldCallCosFunction() {
        Sec sec = new Sec(spyCos);
        sec.calculate(ONE, DEFAULT_PRECISION);

        verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void shouldCalcWithMockCos() {
        BigDecimal arg = new BigDecimal(4);

        when(mockCos.calculate(eq(arg), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("-0.65364362"));

        Sec sec = new Sec(mockCos);
        BigDecimal expectedResult = new BigDecimal("-1.5299");
        assertEquals(expectedResult, sec.calculate(arg, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalcForZero() {
        Sec sec = new Sec();
        assertEquals(
                ONE.setScale(DEFAULT_PRECISION.scale(), HALF_EVEN),
                sec.calculate(ZERO, DEFAULT_PRECISION)
        );
    }

    @Test
    void shouldNotCalcForPiDividedByTwo() {
        Sec sec = new Sec();
        assertThrows(ArithmeticException.class, () -> sec.calculate(HALF_PI, DEFAULT_PRECISION));
    }
}
