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

public class Day8Test {

  private static List<String> input1;

  private static List<String> testInput = 
      List.of(
          "nop +0",
          "acc +1",
          "jmp +4",
          "acc +3",
          "jmp -3",
          "acc -99",
          "acc +1",
          "jmp -4",
          "acc +6"
      );

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day8Test.class.getResource("/Day8_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getAccBeforeRepeat() {
    assertThat(Day8.getAccBeforeRepeat(testInput), is(5));
  }

  @Test
  void test_getAccBeforeRepeat_input1() {
    assertThat(Day8.getAccBeforeRepeat(input1), is(1420));
  }

  @Test
  void test_getAccWithFix() {
    assertThat(Day8.getAccWithFix(testInput), is(8));
  }

  @Test
  void test_getAccWithFix_input1() {
    assertThat(Day8.getAccWithFix(input1), is(1245));
  }
}
