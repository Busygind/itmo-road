package com.busygind;

import com.busygind.function.ApproximatedUnaryFunction;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CsvWriter {

    public static void write(
            String filename,
            ApproximatedUnaryFunction function,
            BigDecimal from,
            BigDecimal to,
            BigDecimal step,
            BigDecimal precision
    ) throws IOException {
        final Path path = Paths.get(filename);
        final File file = new File(path.toUri());
        if (file.exists()) file.delete();
        file.createNewFile();
        final PrintWriter pw = new PrintWriter(file);
        for (BigDecimal current = from; current.compareTo(to) <= 0; current = current.add(step)) {
            pw.println(current + "," + function.calculate(current, precision));
        }
        pw.close();
    }
}
