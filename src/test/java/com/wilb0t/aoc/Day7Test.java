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

public class Day7Test {

  private static List<String> input1;

  private static List<String> testInput;

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day7Test.class.getResource("/Day7_input1.txt").toURI()),
            StandardCharsets.UTF_8);
    
    testInput =
        Files.readAllLines(
            Path.of(Day7Test.class.getResource("/Day7_inputTest.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getBagsThatContain() {
    var bags = Day7.getBagsThatContain(testInput, "shiny gold");
    assertThat(bags.size(), is(4));
  }
  
  @Test
  void test_getBagsThatContain_Input1() {
    var bags = Day7.getBagsThatContain(input1, "shiny gold");
    assertThat(bags.size(), is(144));
  }
  
  @Test
  void test_getContainedBagCount() {
    assertThat(Day7.getContainedBagCount(testInput, "shiny gold"), is(32));
  }

  @Test
  void test_getContainedBagCount_Input1() {
    assertThat(Day7.getContainedBagCount(input1, "shiny gold"), is(5956));
  }
}
