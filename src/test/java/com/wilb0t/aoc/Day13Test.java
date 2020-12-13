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

public class Day13Test {

  private static List<String> input1;

  private static final List<String> testInput1 = 
      List.of(
          "939",
          "7,13,x,x,59,x,31,19");
  
  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day13Test.class.getResource("/Day13_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getBusId() {
    assertThat(Day13.getBusId(testInput1), is(295));
  }

  @Test
  void test_getBusId_input1() {
    assertThat(Day13.getBusId(input1), is(2165));
  }

  @Test
  void test_getTimestamp() {
    assertThat(Day13.getTimestamp(testInput1.get(1)), is(1068781L));
  }

  @Test
  void test_getTimestamp_testInput2() {
    assertThat(Day13.getTimestamp("17,x,13,19"), is(3417L));
  }

  @Test
  void test_getTimestamp_testInput3() {
    assertThat(Day13.getTimestamp("67,7,59,61"), is(754018L));
  }

  @Test
  void test_getTimestamp_testInput4() {
    assertThat(Day13.getTimestamp("67,x,7,59,61"), is(779210L));
  }

  @Test
  void test_getTimestamp_testInput5() {
    assertThat(Day13.getTimestamp("67,7,x,59,61"), is(1261476L));
  }

  @Test
  void test_getTimestamp_testInput6() {
    assertThat(Day13.getTimestamp("1789,37,47,1889"), is(1202161486L));
  }

  @Test
  void test_getTimestamp_input1() {
    assertThat(Day13.getTimestamp(input1.get(1)), is(534035653563227L));
  }
}
