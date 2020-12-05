package com.wilb0t.aoc;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day5 {
  
  private static final int MAX_ROW = 127;
  private static final int MAX_COL = 7;

  public static int getMaxSeatId(List<String> input) {
    return input.stream()
        .map(Day5::getSeatId)
        .max(Comparator.naturalOrder())
        .orElseThrow();
  }
  
  public static int findMissingSeatId(List<String> input) {
    var seatIds = input.stream()
        .map(Day5::getSeatId)
        .sorted()
        .collect(Collectors.toList());
    
    return IntStream.range(0, seatIds.size() - 1)
        .flatMap(idx -> 
            (seatIds.get(idx + 1) - seatIds.get(idx) == 2) 
                ? IntStream.of(seatIds.get(idx) + 1)
                : IntStream.empty()
            )
        .findFirst()
        .orElseThrow();
  }
  
  static int getSeatId(String bsp) {
    return getSeatCol(bsp) + (getSeatRow(bsp) * 8);
  }
  
  static int getSeatRow(String bsp) {
    var lo = 0;
    var hi = MAX_ROW;
    for (int i = 0; i < 6; i++) {
      if (bsp.charAt(i) == 'F') {
        hi = (hi - lo) / 2 + lo;
      } else {
        lo = (hi - lo) / 2 + lo + 1;
      }
    }
    return (bsp.charAt(6) == 'F') ? lo : hi;
  }
  
  static int getSeatCol(String bsp) {
    var lo = 0;
    var hi = MAX_COL;
    for (int i = 7; i < 9; i++) {
      if (bsp.charAt(i) == 'L') {
        hi = (hi - lo) / 2 + lo;
      } else {
        lo = (hi - lo) / 2 + lo + 1;
      }
    }
    return (bsp.charAt(9) == 'L') ? lo : hi;
  } 
}