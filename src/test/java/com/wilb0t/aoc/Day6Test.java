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

public class Day6Test {

  private static List<String> input1;

  private static List<String> testInput;

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day6Test.class.getResource("/Day6_input1.txt").toURI()),
            StandardCharsets.UTF_8);
    
    testInput =
        Files.readAllLines(
            Path.of(Day6Test.class.getResource("/Day6_inputTest.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getDistinctAnswerCountSum() {
    assertThat(Day6.getDistinctAnswerCountSum(testInput), is(11L));
  }

  @Test
  void test_getDistinctAnswerCountSum_input1() {
    assertThat(Day6.getDistinctAnswerCountSum(input1), is(6683L));
  }
  
  @Test
  void test_getAllAnswerCountSum() {
    assertThat(Day6.getAllAnswerCountSum(testInput), is(6L));
  }

  @Test
  void test_getAllAnswerCountSum_input1() {
    assertThat(Day6.getAllAnswerCountSum(input1), is(3122L));
  }
}
