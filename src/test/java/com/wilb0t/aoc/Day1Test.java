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

public class Day1Test {

  private static List<Integer> input1;

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
                Path.of(Day1Test.class.getResource("/Day1_input1.txt").toURI()),
                StandardCharsets.UTF_8)
            .stream()
            .map(Integer::parseInt)
            .collect(Collectors.toList());
  }

  @BeforeEach
  void init() {}

  @Test
  void test_getSumMult() {
    var input = List.of(1721, 979, 366, 299, 675, 1456);
    assertThat(Day1.getSumMult(2020, input), is(Optional.of(514579)));
  }

  @Test
  void test_getSumMult_Input1() {
    assertThat(Day1.getSumMult(2020, input1), is(Optional.of(987339)));
  }

  @Test
  void test_getTripleSumMult() {
    var input = List.of(1721, 979, 366, 299, 675, 1456);
    assertThat(Day1.getTripleSumMult(2020, input), is(Optional.of(241861950)));
  }

  @Test
  void test_getTripleSumMult_Input1() {
    assertThat(Day1.getTripleSumMult(2020, input1), is(Optional.of(259521570)));
  }
}
