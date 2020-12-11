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

public class Day10Test {

  private static List<Integer> input1;

  private static List<Integer> testInput1;
  
  private static List<Integer> testInput2;

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
                Path.of(Day10Test.class.getResource("/Day10_input1.txt").toURI()),
                StandardCharsets.UTF_8)
            .stream()
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    
    testInput1 =
        Files.readAllLines(
            Path.of(Day10Test.class.getResource("/Day10_inputTest1.txt").toURI()),
            StandardCharsets.UTF_8)
            .stream()
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    
    testInput2 =
        Files.readAllLines(
            Path.of(Day10Test.class.getResource("/Day10_inputTest2.txt").toURI()),
            StandardCharsets.UTF_8)
            .stream()
            .map(Integer::parseInt)
            .collect(Collectors.toList());
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getJoltDiffNum() {
    assertThat(Day10.getJoltDiffNum(testInput1), is(7 * 5));
    assertThat(Day10.getJoltDiffNum(testInput2), is(22 * 10));
  }

  @Test
  void test_getJoltDiffNum_input1() {
    assertThat(Day10.getJoltDiffNum(input1), is(2263));
  }

  @Test
  void test_getValidCombs() {
    assertThat(Day10.getValidCombos(testInput1), is(8L));
    assertThat(Day10.getValidCombos(testInput2), is(19208L));
  }

  @Test
  void test_getValidCombs_input() {
    assertThat(Day10.getValidCombos(input1), is(396857386627072L));
  }
}
