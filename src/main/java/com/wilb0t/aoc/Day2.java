package com.wilb0t.aoc;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day2 {

  public static List<String> getValidPasswordsRangeRule(List<String> input) {
    return input.stream()
        .filter(line -> {
          var parts = line.split(" ");
          var rangeParts = parts[0].split("-");
          
          var lo = Integer.parseInt(rangeParts[0]);
          var hi = Integer.parseInt(rangeParts[1]);
          var letter = parts[1].charAt(0);
          
          var letterCount = parts[2].chars().filter(i -> i == letter).count();
          return (letterCount >= lo) && (letterCount <= hi);
        })
        .collect(Collectors.toList());
  }
  
  public static List<String> getValidPasswordsPositionRule(List<String> input) {
    return input.stream()
        .filter(line -> {
          var parts = line.split(" ");
          var rangeParts = parts[0].split("-");

          var pos1 = Integer.parseInt(rangeParts[0]);
          var pos2 = Integer.parseInt(rangeParts[1]);
          var letter = parts[1].charAt(0);

          return (parts[2].charAt(pos1 - 1) == letter) ^ (parts[2].charAt(pos2 - 1) == letter);
        })
        .collect(Collectors.toList());
  }
}
