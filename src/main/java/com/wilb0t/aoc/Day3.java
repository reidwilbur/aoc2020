package com.wilb0t.aoc;

import java.util.List;
import java.util.stream.IntStream;

class Day3 {

  private static final char TREE = '#';

  public record Slope(int x, int y) {}

  public static long getTreeCount(Slope slope, List<String> input) {
    var width = input.get(0).length();
    return IntStream.range(0, input.size() / slope.y)
        .filter(idx -> input.get(idx * slope.y).charAt((idx * slope.x) % width) == TREE)
        .count();
  }

  public static long getTreeCountMult(List<Slope> slopes, List<String> input) {
    return slopes.stream()
        .map(slope -> getTreeCount(slope, input))
        .reduce((l, r) -> l * r)
        .orElseThrow();
  }
}
