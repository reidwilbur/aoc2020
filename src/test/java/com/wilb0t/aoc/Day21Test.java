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

public class Day21Test {

  private static List<String> input1;

  private static final List<String> testInput1 =
      List.of(
          "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)",
          "trh fvjkl sbzzf mxmxvkd (contains dairy)",
          "sqjhc fvjkl (contains soy)",
          "sqjhc mxmxvkd sbzzf (contains fish)"
      );

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day21Test.class.getResource("/Day21_input1.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() throws Exception {}

  @Test
  void test_() {
  }
}
