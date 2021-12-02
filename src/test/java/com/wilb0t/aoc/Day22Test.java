package com.wilb0t.aoc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day22Test {

  private static List<String> input1;

  private static final List<String> testInput1 =
      List.of(
      );

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day22Test.class.getResource("/Day22_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() throws Exception {}

  @Test
  void test_playCombat() {
    var input = List.of(
        List.of(9, 2, 6, 3, 1),
        List.of(5, 8, 4, 7, 10)
    );
    assertThat(Day22.playCombat(input), is(306L));
  }

  @Test
  void test_playCombat_input1() {
    var input = Day22.parseInput(input1);
    assertThat(Day22.playCombat(input), is(36257L));
  }

  void test_playRecurCombat() {
    var input = List.of(
        List.of(9, 2, 6, 3, 1),
        List.of(5, 8, 4, 7, 10)
    );
    assertThat(Day22.playRecurCombat(input), is(291L));
  }

  void test_playRecurCombat_input1() {
    var input = Day22.parseInput(input1);
    assertThat(Day22.playRecurCombat(input), is(291L));
  }
}
