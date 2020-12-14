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

public class Day14Test {

  private static List<String> input1;

  private static final List<String> testInput1 = 
      List.of(
          "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
          "mem[8] = 11",
          "mem[7] = 101",
          "mem[8] = 0");

  private static final List<String> testInput2 =
      List.of(
          "mask = 000000000000000000000000000000X1001X",
          "mem[42] = 100",
          "mask = 00000000000000000000000000000000X0XX",
          "mem[26] = 1");

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day14Test.class.getResource("/Day14_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_sumOfMemWithMask() {
    assertThat(Day14.sumOfMemWithMask(testInput1), is(165L));
  }

  @Test
  void test_sumOfMemWithMask_input1() {
    assertThat(Day14.sumOfMemWithMask(input1), is(7611244640053L));
  }

  @Test
  void test_sumOfMemWithFloaters() {
    assertThat(Day14.sumOfMemWithFloaters(testInput2), is(208L));
  }

  @Test
  void test_sumOfMemWithFloaters_input1() {
    assertThat(Day14.sumOfMemWithFloaters(input1), is(3705162613854L));
  }
}
