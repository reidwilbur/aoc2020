package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day11Test {

  private static List<String> input1;

  private static List<String> testInput1;
  
  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
                Path.of(Day11Test.class.getResource("/Day11_input1.txt").toURI()),
                StandardCharsets.UTF_8);
    
    testInput1 =
        Files.readAllLines(
            Path.of(Day11Test.class.getResource("/Day11_inputTest.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getSeatCount() {
    assertThat(Day11.getSeatCount(testInput1, false), is(37));
  }

  @Test
  void test_getSeatCount_input1() {
    assertThat(Day11.getSeatCount(input1, false), is(2194));
  }

  @Test
  void test_getSeatCount_part2() {
    assertThat(Day11.getSeatCount(testInput1, true), is(26));
  }

  @Test
  void test_getSeatCount_part2_input1() {
    assertThat(Day11.getSeatCount(input1, true), is(1944));
  }
}
