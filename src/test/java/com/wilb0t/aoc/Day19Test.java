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

public class Day19Test {

  private static List<String> input1;

  private static final List<String> testInput1 = 
      List.of(
        "0: 4 1 5",
        "1: 2 3 | 3 2",
        "2: 4 4 | 5 5",
        "3: 4 5 | 5 4",
        "4: \"a\"",
        "5: \"b\"", 
        "",
        "ababbb",
        "bababa",
        "abbbab",
        "aaabbb",
        "aaaabbb "
      );
  
  private static List<String> testInput2;

  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day19Test.class.getResource("/Day19_input1.txt").toURI()),
            StandardCharsets.UTF_8);
    
    testInput2 =
        Files.readAllLines(
            Path.of(Day19Test.class.getResource("/Day19_inputTest.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() throws Exception {}

  @Test
  void test_getMatchCount() {
    assertThat(Day19.getMatchCount(testInput1, 0), is(2L));
  }

  @Test
  void test_getMatchCount_input1() {
    assertThat(Day19.getMatchCount(input1, 0), is(220L));
  }

  @Test
  void test_getMatchesPatched() {
    assertThat(
        Day19.getMatchesPatched(testInput2, 0), 
        is(
            List.of("babbbbaabbbbbabbbbbbaabaaabaaa"))
//            List.of(
//                "bbabbbbaabaabba",
//                "babbbbaabbbbbabbbbbbaabaaabaaa",
//                "aaabbbbbbaaaabaababaabababbabaaabbababababaaa",
//                "bbbbbbbaaaabbbbaaabbabaaa",
//                "bbbababbbbaaaaaaaabbababaaababaabab",
//                "ababaaaaaabaaab",
//                "ababaaaaabbbaba",
//                "baabbaaaabbaaaababbaababb",
//                "abbbbabbbbaaaababbbbbbaaaababb",
//                "aaaaabbaabaaaaababaa",
//                "aaaabbaabbaaaaaaabbbabbbaaabbaabaaa",
//                "aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"))
    );
  }
}
