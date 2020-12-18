package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day18Test {

  private static List<String> input1;

  private static final List<String> testInput1 = 
      List.of(
          "1 + 2 * 3 + 4 * 5 + 6",
          "1 + (2 * 3) + (4 * (5 + 6))",
          "2 * 3 + (4 * 5)",
          "5 + (8 * 3 + 9 + 3 * 4 * 3)",
          "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))",
          "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
      );

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day18Test.class.getResource("/Day18_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() throws Exception {}

  @Test
  void test_eval() {
    assertThat(Day18.eval(testInput1), is(List.of(71L, 51L, 26L, 437L, 12240L, 13632L)));
  }

  @Test
  void test_eval_input1() {
    var vals = Day18.eval(input1);
    assertThat(vals.stream().mapToLong(l -> l).sum(), is(6923486965641L));
  }
  
  @Test
  void test_evalPlusPlus() {
    assertThat(Day18.evalPlusPlus(testInput1), is(List.of(231L, 51L, 46L, 1445L, 669060L, 23340L)));
  }

  @Test
  void test_evalPlusPlus_input1() {
    var vals = Day18.evalPlusPlus(input1);
    assertThat(vals.stream().mapToLong(l -> l).sum(), is(70722650566361L));
  }
}
