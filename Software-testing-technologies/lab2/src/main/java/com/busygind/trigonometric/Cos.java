package com.busygind.trigonometric;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.*;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.busygind.function.CheckedApproximatedUnaryFunction;

import java.math.BigDecimal;
import java.math.MathContext;

public class Cos extends CheckedApproximatedUnaryFunction {

    private final Sin sin;

    public Cos() {
        super();
        this.sin = new Sin();
    }

    public Cos(Sin sin) {
        super();
        this.sin = sin;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) throws ArithmeticException {
        checkArguments(x, precision);

        final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        final BigDecimal correctedX = x.remainder(BigDecimalMath.pi(mc).multiply(new BigDecimal(2)));

        if (correctedX.compareTo(ZERO) == 0) return ONE;

        final BigDecimal result =
                sin.calculate(
                        BigDecimalMath.pi(mc)
                                .divide(new BigDecimal(2), DECIMAL128.getPrecision(), HALF_EVEN)
                                .subtract(correctedX),
                        precision);
        return result.setScale(precision.scale(), HALF_EVEN);
    }
}
