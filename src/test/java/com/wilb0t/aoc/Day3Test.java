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

public class Day3Test {

  private static List<String> input1;

  private static final List<String> testInput =
      List.of(
          "..##.......",
          "#...#...#..",
          ".#....#..#.",
          "..#.#...#.#",
          ".#...##..#.",
          "..#.##.....",
          ".#.#.#....#",
          ".#........#",
          "#.##...#...",
          "#...##....#",
          ".#..#...#.#");

  private static final List<Day3.Slope> slopes =
      List.of(
          new Day3.Slope(1, 1),
          new Day3.Slope(3, 1),
          new Day3.Slope(5, 1),
          new Day3.Slope(7, 1),
          new Day3.Slope(1, 2));

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day3Test.class.getResource("/Day3_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getTreeCount() {
    assertThat(Day3.getTreeCount(new Day3.Slope(3, 1), testInput), is(7L));
  }

  @Test
  void test_getTreeCount_Input1() {
    assertThat(Day3.getTreeCount(new Day3.Slope(3, 1), input1), is(178L));
  }

  @Test
  void test_getTreeCountMult() {
    assertThat(Day3.getTreeCountMult(slopes, testInput), is(336L));
  }

  @Test
  void test_getTreeCountMult_Input1() {
    assertThat(Day3.getTreeCountMult(slopes, input1), is(3492520200L));
  }
}
