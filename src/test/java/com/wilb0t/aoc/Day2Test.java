package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day2Test {

  private static List<String> input1;
  
  private static List<String> testInput =
      List.of(
          "1-3 a: abcde", 
          "1-3 b: cdefg", 
          "2-9 c: ccccccccc"
      );

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
                Path.of(Day2Test.class.getResource("/Day2_input1.txt").toURI()),
                StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getValidPasswordsRangeRule() {
    assertThat(Day2.getValidPasswordsRangeRule(testInput).size(), is(2));
  }
  
  @Test
  void test_getValidPasswordsRangeRule_Input1() {
    assertThat(Day2.getValidPasswordsRangeRule(input1).size(), is(580));
  }
  
  @Test
  void test_getValidPasswordsPosRule() {
    assertThat(Day2.getValidPasswordsPositionRule(testInput).size(), is(1));
  }

  @Test
  void test_getValidPasswordsPosRule_Input1() {
    assertThat(Day2.getValidPasswordsPositionRule(input1).size(), is(611));
  }
}
