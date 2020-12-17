package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day17Test {

  private static List<String> input1 =
      List.of(
          ".##.##..",
          "..###.##",
          ".##....#",
          "###..##.",
          "#.###.##",
          ".#.#..#.",
          ".......#",
          ".#..#..#");

  private static List<String> testInput1 = 
      List.of(
          ".#.", 
          "..#", 
          "###");

  @BeforeAll
  static void initAll() throws Exception {}

  @BeforeEach
  void init() throws Exception {}

  @Test
  void test_simulate() {
    assertThat(Day17.simulate(testInput1, 1), is(11));
    assertThat(Day17.simulate(testInput1, 2), is(21));
    assertThat(Day17.simulate(testInput1, 6), is(112));
  }

  @Test
  void test_simulate_input1() {
    assertThat(Day17.simulate(input1, 6), is(257));
  }

  @Test
  void test_simulate4() {
    assertThat(Day17.simulate4(testInput1, 1), is(29));
    assertThat(Day17.simulate4(testInput1, 6), is(848));
  }

  @Test
  void test_simulate4_input1() {
    assertThat(Day17.simulate4(input1, 6), is(2532));
  }
}
