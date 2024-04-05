package com.busygind;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.stream.Stream;

import com.busygind.function.ApproximatedUnaryFunction;
import com.busygind.logariphmic.Ln;
import com.busygind.logariphmic.Log;
import com.busygind.trigonometric.Cos;
import com.busygind.trigonometric.Cot;
import com.busygind.trigonometric.Csc;
import com.busygind.trigonometric.Sec;
import com.busygind.trigonometric.Sin;
import com.busygind.trigonometric.Tan;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ApproximatedUnaryFunctionTest {
    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.000001");

    @ParameterizedTest
    @MethodSource("modules")
    void calcWithNullArgumentShouldThrow(ApproximatedUnaryFunction function) {
        assertThrows(NullPointerException.class, () -> function.calculate(null, DEFAULT_PRECISION));
    }

    @ParameterizedTest
    @MethodSource("modules")
    void calcWithNullPrecisionShouldThrow(ApproximatedUnaryFunction function) {
        assertThrows(NullPointerException.class, () -> function.calculate(ONE, null));
    }

    private static Stream<Arguments> modules() {
        return Stream.of(
                Arguments.of(new Sin()),
                Arguments.of(new Cos()),
                Arguments.of(new Tan()),
                Arguments.of(new Cot()),
                Arguments.of(new Sec()),
                Arguments.of(new Csc()),
                Arguments.of(new Ln()),
                Arguments.of(new Log(2)),
                Arguments.of(new Log(5)),
                Arguments.of(new Log(10)));
    }
}
