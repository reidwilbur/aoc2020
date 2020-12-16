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

public class Day16Test {

  private static List<String> input1;

  private static List<String> testInput1;

  @BeforeAll
  static void initAll() throws Exception {}

  @BeforeEach
  void init() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day16Test.class.getResource("/Day16_input1.txt").toURI()),
            StandardCharsets.UTF_8);

    testInput1 =
        Files.readAllLines(
            Path.of(Day16Test.class.getResource("/Day16_inputTest.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @Test
  void test_getScanningErrorRate() {
    assertThat(Day16.getScanningErrorRate(testInput1), is(71L));
  }

  @Test
  void test_getScanningErrorRate_input1() {
    assertThat(Day16.getScanningErrorRate(input1), is(27850L));
  }

  @Test
  void test_getDepartureValue_input1() {
    assertThat(Day16.getDepartureValue(input1), is(491924517533L));
  }
}
