package com.busygind.trigonometric;

import com.busygind.function.CheckedApproximatedUnaryFunction;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_EVEN;
import static java.lang.String.format;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;

public class Sec extends CheckedApproximatedUnaryFunction {

    private final Cos cos;
    public Sec() {
        super();
        this.cos = new Cos();
    }

    public Sec(final Cos cos) {
        super();
        this.cos = cos;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) throws ArithmeticException {
        checkArguments(x, precision);

        final BigDecimal cosValue = cos.calculate(x, precision);

        if (cosValue.compareTo(ZERO) == 0) {
            throw new ArithmeticException(format("Function value for argument %s doesn't exist", x));
        }

        final BigDecimal result = ONE.divide(cosValue, DECIMAL128.getPrecision(), HALF_EVEN);
        return result.setScale(precision.scale(), HALF_EVEN);
    }
}
