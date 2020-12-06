package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class Day6 {
  
  public static record Answers (int people, String answers) { 
    public long getAllAnswerCount() {
      var charCounts = answers.chars().mapToObj(c -> (char) c)
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      return charCounts.entrySet()
          .stream().filter(e -> e.getValue() == (long) people)
          .count();
    }
    
    public long getDistinctAnswerCount() {
      return answers.chars().distinct().count();
    }
  };
  
  public static long getDistinctAnswerCountSum(List<String> input) {
    return collapse(input).stream()
        .map(Answers::getDistinctAnswerCount)
        .reduce(Long::sum)
        .orElseThrow();
  }
  
  public static long getAllAnswerCountSum(List<String> input) {
    return collapse(input).stream()
        .map(Answers::getAllAnswerCount)
        .reduce(Long::sum)
        .orElseThrow();
  }
  
  public static List<Answers> collapse(List<String> input) {
    var concats = new ArrayList<Answers>();
    var idx = 0;
    for (var line : input) {
      if (line.isEmpty()) {
        idx += 1;
      } else {
        if (concats.size() == idx) {
          concats.add(new Answers(1, line.strip()));
        } else {
          var cur = concats.get(idx);
          concats.set(idx, new Answers(cur.people + 1, cur.answers + line.strip()));
        }
      }
    }
    return concats;    
  }

}