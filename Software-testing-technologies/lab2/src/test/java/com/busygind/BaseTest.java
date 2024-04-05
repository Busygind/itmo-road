package com.busygind;

import ch.obermuhlner.math.big.BigDecimalMath;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

@ExtendWith(MockitoExtension.class)
public class BaseTest {
    protected static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.0001");
    protected static final BigDecimal HALF_PI = BigDecimalMath.pi(new MathContext(DECIMAL128.getPrecision())).divide(new BigDecimal(2), DECIMAL128.getPrecision(), HALF_EVEN);
}
