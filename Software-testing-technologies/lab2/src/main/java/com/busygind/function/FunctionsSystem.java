package com.busygind.function;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.busygind.logariphmic.Ln;
import com.busygind.logariphmic.Log;
import com.busygind.trigonometric.Cos;
import com.busygind.trigonometric.Cot;
import com.busygind.trigonometric.Csc;
import com.busygind.trigonometric.Sec;
import com.busygind.trigonometric.Sin;
import com.busygind.trigonometric.Tan;

import java.math.BigDecimal;
import java.math.MathContext;

public class FunctionsSystem implements ApproximatedUnaryFunction {

    private final Sin sin;
    private final Cos cos;
    private final Tan tan;
    private final Cot cot;
    private final Sec sec;
    private final Csc csc;
    private final Ln ln;
    private final Log log2;
    private final Log log5;
    private final Log log10;

    public FunctionsSystem() {
        this.sin = new Sin();
        this.cos = new Cos();
        this.tan = new Tan();
        this.cot = new Cot();
        this.sec = new Sec();
        this.csc = new Csc();
        this.ln = new Ln();
        this.log2 = new Log(2);
        this.log5 = new Log(5);
        this.log10 = new Log(10);
    }

    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        BigDecimal correctedX = x.remainder(BigDecimalMath.pi(mc).multiply(new BigDecimal(2)));
        if (x.compareTo(ZERO) <= 0) {
            return (
                    (
                            cot.calculate(correctedX, precision).divide(sec.calculate(correctedX, precision), HALF_UP)
                                    .divide(sin.calculate(correctedX, precision), HALF_UP)
                                    .divide((sec.calculate(correctedX, precision).pow(2)), HALF_UP)
                                    .divide((cos.calculate(correctedX, precision).pow(2)), HALF_UP)
                    ).multiply(
                            cos.calculate(correctedX, precision).pow(2)
                                    .subtract(cot.calculate(correctedX, precision))
                                    .add(csc.calculate(correctedX, precision))
                                    .divide(
                                            csc.calculate(correctedX, precision)
                                                    .multiply(tan.calculate(correctedX, precision)),
                                            HALF_UP
                                    )
                    )
            ).setScale(precision.scale(), HALF_EVEN);
        } else {
            if (ln.calculate(correctedX, precision).equals(ZERO)) return null;
            return (log10.calculate(correctedX, precision)
                    .multiply(log5.calculate(correctedX, precision))
                    .multiply(log2.calculate(correctedX, precision))
                    .subtract(log2.calculate(correctedX, precision))
                    .subtract(log10.calculate(correctedX, precision))
                    .add(log5.calculate(correctedX, precision))
                    .multiply(
                            ln.calculate(correctedX, precision)
                                    .add(log5.calculate(correctedX, precision))
                                    .divide(ln.calculate(correctedX, precision).pow(2), HALF_UP).pow(3)
                    ).pow(3)
            ).setScale(precision.scale(), HALF_EVEN);
        }
    }
}
