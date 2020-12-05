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

public class Day5Test {

  private static List<String> input1;

  private static List<String> testInput =
      List.of(
          "FBFBBFFRLR",
          "BFFFBBFRRR",
          "FFFBBBFRRR",
          "BBFFBBFRLL"
      );


  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day5Test.class.getResource("/Day5_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getMaxSeatId() {
    assertThat(Day5.getMaxSeatId(testInput), is(820));
  }
  
  @Test
  void test_geMaxSeatId_input1() {
    assertThat(Day5.getMaxSeatId(input1), is(855));
  }
  
  @Test
  void test_getSeatRow() {
    assertThat(Day5.getSeatRow("FBFBBFFRLR"), is(44));
  }
  
  @Test
  void test_getSeatCol() {
    assertThat(Day5.getSeatCol("FBFBBFFRLR"), is(5));
  }
  
  @Test
  void test_findMissingSeatId() {
    assertThat(Day5.findMissingSeatId(input1), is(552));
  }
}
