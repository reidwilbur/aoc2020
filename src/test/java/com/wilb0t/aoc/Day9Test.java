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

public class Day9Test {

  private static List<Long> input1;

  private static List<Long> testInput;

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
                Path.of(Day9Test.class.getResource("/Day9_input1.txt").toURI()),
                StandardCharsets.UTF_8)
            .stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());

    testInput =
        Files.readAllLines(
                Path.of(Day9Test.class.getResource("/Day9_inputTest.txt").toURI()),
                StandardCharsets.UTF_8)
            .stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
  }

  @BeforeEach
  void init() {}

  @Test
  void test_findXMASWeakness() {
    assertThat(Day9.findXMASWeakness(5, testInput), is(List.of(127L)));
  }

  @Test
  void test_findXMASWeakness_input1() {
    assertThat(Day9.findXMASWeakness(25, input1), is(List.of(15353384L)));
  }

  @Test
  void test_findWeaknesRange() {
    assertThat(Day9.findWeaknessRange(127L, testInput), is(62L));
  }

  @Test
  void test_findWeaknesRange_input1() {
    assertThat(Day9.findWeaknessRange(15353384L, input1), is(2466556L));
  }
}
