package com.busygind.trigonometric;

import com.busygind.function.CheckedApproximatedUnaryFunction;

import java.math.BigDecimal;

import static java.lang.String.format;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

public class Csc extends CheckedApproximatedUnaryFunction {
    private final Sin sin;
    public Csc() {
        super();
        this.sin = new Sin();
    }

    public Csc(Sin sin) {
        super();
        this.sin = sin;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) throws ArithmeticException {
        checkArguments(x, precision);

        BigDecimal sinValue = sin.calculate(x, precision);

        if (sinValue.compareTo(ZERO) == 0) {
            throw new ArithmeticException(format("Function value for argument %s doesn't exist", x));
        }

        BigDecimal result = ONE.divide(sinValue, DECIMAL128.getPrecision(), HALF_EVEN);
        return result.setScale(precision.scale(), HALF_EVEN);
    }
}
