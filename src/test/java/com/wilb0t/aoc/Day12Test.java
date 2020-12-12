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

public class Day12Test {

  private static List<String> input1;

  private static final List<String> testInput1 = 
      List.of(
          "F10", 
          "N3", 
          "F7", 
          "R90", 
          "F11");

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day12Test.class.getResource("/Day12_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getDist() {
    assertThat(Day12.getDist(testInput1), is(25));
  }

  @Test
  void test_getDist_input1() {
    assertThat(Day12.getDist(input1), is(1032));
  }
  
  @Test
  void test_getDistWaypoint() {
    assertThat(Day12.getDistWaypoint(testInput1), is(286));
  }

  @Test
  void test_getDistWaypoint_input1() {
    assertThat(Day12.getDistWaypoint(input1), is(156735));
  }
}
