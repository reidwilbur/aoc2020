package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day15Test {

  private static final List<Integer> input1 =
    Arrays.stream("1,12,0,20,8,16".split(","))
      .map(Integer::parseInt)
      .collect(Collectors.toList());

  private static final List<Integer> testInput1 =
      Arrays.stream("0,3,6".split(","))
          .map(Integer::parseInt)
          .collect(Collectors.toList());

  @BeforeAll
  static void initAll() throws Exception { }

  @BeforeEach
  void init() {}

  @Test
  void test_getNumber() {
    assertThat(Day15.getNumber(testInput1, 1), is(0));
    assertThat(Day15.getNumber(testInput1, 2), is(3));
    assertThat(Day15.getNumber(testInput1, 3), is(6));
    assertThat(Day15.getNumber(testInput1, 4), is(0));
    assertThat(Day15.getNumber(testInput1, 5), is(3));
    assertThat(Day15.getNumber(testInput1, 6), is(3));
    assertThat(Day15.getNumber(testInput1, 7), is(1));
    assertThat(Day15.getNumber(testInput1, 8), is(0));
    assertThat(Day15.getNumber(testInput1, 9), is(4));
    assertThat(Day15.getNumber(testInput1, 10), is(0));
    assertThat(Day15.getNumber(testInput1, 2020), is(436));
    //assertThat(Day15.getNumber(testInput1, 30000000), is(175594));
  }

  @Test
  void test_getNumber_input1() {
    assertThat(Day15.getNumber(input1, 2020), is(273));
    //assertThat(Day15.getNumber(input1, 30000000), is(47205));
  }
}
