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

public class Day4Test {

  private static List<String> input1;

  private static List<String> testInput;

  private static List<String> validPassports;

  private static List<String> invalidPassports;

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day4Test.class.getResource("/Day4_input1.txt").toURI()),
            StandardCharsets.UTF_8);

    testInput =
        Files.readAllLines(
            Path.of(Day4Test.class.getResource("/Day4_inputTest.txt").toURI()),
            StandardCharsets.UTF_8);

    validPassports =
        Files.readAllLines(
            Path.of(Day4Test.class.getResource("/Day4_valid.txt").toURI()), StandardCharsets.UTF_8);

    invalidPassports =
        Files.readAllLines(
            Path.of(Day4Test.class.getResource("/Day4_invalid.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getValidPassports() {
    assertThat(Day4.getValidPassports(testInput).size(), is(2));
  }

  @Test
  void test_getValidPassports_Input1() {
    assertThat(Day4.getValidPassports(input1).size(), is(216));
  }

  @Test
  void test_getValidatedPassports_AllValid() {
    var valid = Day4.getValidatedPassports(validPassports);
    assertThat(valid.size(), is(4));
  }

  @Test
  void test_getValidatedPassports_AllInvalid() {
    assertThat(Day4.getValidatedPassports(invalidPassports).size(), is(0));
  }

  @Test
  void test_getValidatedPassports_Input1() {
    var valid = Day4.getValidatedPassports(input1);
    assertThat(valid.size(), is(150));
  }
}
