package com.wilb0t.aoc;

import org.immutables.value.Value;
import java.util.List;
import java.util.stream.Collectors;

class Day2 {

  @Value.Immutable
  public interface PasswordEntry {
    int lo();

    int hi();

    char letter();

    String password();

    static PasswordEntry fromString(String line) {
      var parts = line.split(" ");
      var rangeParts = parts[0].split("-");

      var lo = Integer.parseInt(rangeParts[0]);
      var hi = Integer.parseInt(rangeParts[1]);
      var letter = parts[1].charAt(0);

      return ImmutablePasswordEntry.builder()
          .lo(lo)
          .hi(hi)
          .letter(letter)
          .password(parts[2])
          .build();
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
