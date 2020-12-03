package com.wilb0t.aoc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.math3.util.Pair;

class Day3 {

  private static final char TREE = '#';

  public static long getTreeCount(int xSlope, int ySlope, List<String> input) {
    var width = input.get(0).length();
    return IntStream.range(0, input.size() / ySlope)
        .filter(idx -> input.get(idx * ySlope).charAt((idx * xSlope) % width) == TREE)
        .count();
  }

  public static long getTreeCountMult(
      List<Map.Entry<Integer, Integer>> slopes, List<String> input) {
    return slopes.stream()
        .map(slope -> getTreeCount(slope.getKey(), slope.getValue(), input))
        .reduce((l, r) -> l * r)
        .orElseThrow();
  }
}
