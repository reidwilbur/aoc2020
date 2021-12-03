package com.wilb0t.aoc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Day24Test {

  private static List<String> puzzleInput;

  private static List<String> testInput;

  @BeforeAll
  static void initAll() throws Exception {
    testInput =
        Files.readAllLines(
            Path.of(Day24Test.class.getResource("/Day24_inputTest.txt").toURI()), 
            StandardCharsets.UTF_8);
    puzzleInput =
        Files.readAllLines(
            Path.of(Day24Test.class.getResource("/Day24_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @Test
  void test_getBlackTileCount_testInput() {
    assertThat(Day24.getBlackTileCount(testInput), is(10));
  }

  @Test
  void test_getBlackTileCount_puzzleInput() {
    assertThat(Day24.getBlackTileCount(puzzleInput), is(373));
  }
  
  @Test
  void test_getGolTileCount_testInput() {
    assertThat(Day24.getGolTileCount(testInput, 100), is(2208));
  }

  @Test
  void test_getGolTileCount_puzzleInput() {
    assertThat(Day24.getGolTileCount(puzzleInput, 100), is(3917));
  }
}
