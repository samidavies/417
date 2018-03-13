package cse417;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleFunction;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static cse417.GraphUtils.EPSILON;


/**
 * Program that reads a table of numbers stored in a CSV and writes a new CSV
 * with the entries in the table rounded in such a way that the colum and row
 * sums are equal to the correct sums rounded.
 */
public class TableRounder {

  /** Entry point for a program to round table entries. */
  public static void main(String[] args) throws IOException {
    ArgParser argParser = new ArgParser("Table");
    argParser.addOption("header", Boolean.class);
    argParser.addOption("digits", Integer.class);
    argParser.addOption("out-file", String.class);
    args = argParser.parseArgs(args, 1, 1);

    // If the user asks us to round to a digit after the decimal place, we
    // multiply by a power of 10 so that rounding integers after scaling is the
    // same as rounding at the desired decimal place. (We scale back below.)
    int digits = argParser.hasOption("digits") ?
        argParser.getIntegerOption("digits") : 0;
    final double scale = Math.pow(10, digits);

    CsvParser csvParser = new CsvParser(args[0]);
    String[] header = null;
    if (argParser.hasOption("header")) {
      assert csvParser.hasNext();
      header = csvParser.next();
    }

    // Read the table from the CSV.
    List<double[]> table = new ArrayList<double[]>();
    while (csvParser.hasNext()) {
      table.add(Arrays.asList(csvParser.next()).stream()
          .mapToDouble(s -> scale * Double.parseDouble(s)).toArray());
      if (table.size() > 2) {
        assert table.get(table.size()-2).length ==
               table.get(table.size()-1).length;
      }
    }

    roundTable(table);

    // Output the rounded tables.
    PrintStream output = !argParser.hasOption("out-file") ? System.out :
        new PrintStream(new FileOutputStream(
            argParser.getStringOption("out-file")));
    if (header != null)
      writeRow(output, header);  // echo the header to the output
    for (double[] vals : table) {
      writeRow(output,
          DoubleStream.of(vals).map(v -> v / scale).toArray(), digits);
    }
  }

  /** Modifies the given table so that each entry is rounded to an integer. */
  static void roundTable(final List<double[]> table) {
    if (table.size() == 0) return;

    // TODO: implement this
  }

  /**
   * Returns a flow that satisfies the given constraints or null if none
   * exists.
   */
  static ToDoubleBiFunction<Integer, Integer> findFeasibleBoundedFlow(
      final Integer source, final Integer sink, Collection<Integer> nodes,
      ToDoubleBiFunction<Integer, Integer> minEdgeFlow,
      ToDoubleBiFunction<Integer, Integer> maxEdgeFlow) {

    // TODO: implement this properly
    return (a, b) -> 0.0;
  }

  /**
   * Returns a circulation that satisfies the given capacity constraints (upper
   * bounds) and demands or null if none exists.
   */
  static ToDoubleBiFunction<Integer, Integer> findFeasibleDemandFlow(
      Collection<Integer> nodes,
      final ToDoubleBiFunction<Integer, Integer> capacity,
      final ToDoubleFunction<Integer> demand) {

    // Make sure that the demands could even possibly be met.
    double surplus = 0, deficit = 0;
    for (Integer n : nodes) {
      if (demand.applyAsDouble(n) >= EPSILON)
        surplus += demand.applyAsDouble(n);
      if (demand.applyAsDouble(n) <= -EPSILON)
        deficit += -demand.applyAsDouble(n);
    }
    assert Math.abs(surplus - deficit) <= 1e-5;

    // TODO: implement this properly
    return (a, b) -> 0.0;
  }

  /**
   * Outputs a CSV row of the given values with the specified number of digits
   * after the decimal.
   */
  private static void writeRow(PrintStream out, double[] vals, int digits) {
    final String fmt = String.format("%%.%df", digits);
    DoubleFunction<String> fmtVal = v -> String.format(fmt, v);
    writeRow(out, DoubleStream.of(vals).mapToObj(fmtVal)
        .toArray(n -> new String[n]));
  }

  /**
   * Outputs a CSV row containing the given values. Note that the current
   * implementation assumes that there are no commas in the column values.
   */
  private static void writeRow(PrintStream out, String[] row) {
    for (int i = 0; i < row.length; i++)
      assert row[i].indexOf(',') < 0;  // quoting not supported here

    out.println(Stream.of(row).collect(Collectors.joining(",")).toString());
  }
}
