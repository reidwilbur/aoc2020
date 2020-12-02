package com.wilb0t.aoc;

import java.util.List;
import java.util.stream.Collectors;

class Day2 {

  public static record PasswordEntry(int lo, int hi, char letter, String password) {
    public static PasswordEntry fromString(String line) {
      var parts = line.split(" ");
      var rangeParts = parts[0].split("-");

      var lo = Integer.parseInt(rangeParts[0]);
      var hi = Integer.parseInt(rangeParts[1]);
      var letter = parts[1].charAt(0);
      
      return new PasswordEntry(lo, hi, letter, parts[2]);
    }
  }

  public static List<PasswordEntry> getValidPasswordsRangeRule(List<String> input) {
    return input.stream()
        .map(PasswordEntry::fromString)
        .filter(
            entry -> {
              var letterCount =
                  entry.password().chars().filter(i -> i == entry.letter()).count();
              return (letterCount >= entry.lo()) && (letterCount <= entry.hi());
            })
        .collect(Collectors.toList());
  }

  public static List<PasswordEntry> getValidPasswordsPositionRule(List<String> input) {
    return input.stream()
        .map(PasswordEntry::fromString)
        .filter(
            entry ->
                (entry.password().charAt(entry.lo() - 1) == entry.letter())
                    ^ (entry.password().charAt(entry.hi() - 1)) == entry.letter())
        .collect(Collectors.toList());
  }
}
