package com.wilb0t.aoc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

class Day13 {

  public static int getBusId(List<String> input) {
    var time = Integer.parseInt(input.get(0));
    var busIds = Arrays.stream(input.get(1).split(","))
        .filter(Predicate.not("x"::equals))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    
    var entry = busIds.stream()
        .map(id -> Map.entry(id, id * ((time / id) + ((time % id > 0) ? 1 : 0))))
        .min(Comparator.comparingInt(Map.Entry::getValue))
        .orElseThrow();
        
    return entry.getKey() * (entry.getValue() - time);
  }
  
  static class SolveAcc {
    long time;
    long base;
  }
  
  public static long getTimestamp(String input) {
    var splitInput= Arrays.stream(input.split(","))
        .collect(Collectors.toList());

    var idOffsets = IntStream.range(0, splitInput.size())
        .boxed()
        .filter(Predicate.not(idx -> splitInput.get(idx).equals("x")))
        .map(idx -> Map.entry(Integer.parseInt(splitInput.get(idx)), idx))
        .collect(Collectors.toList());
    
    long base = idOffsets.get(0).getKey();
    long time = solve(0, base, idOffsets.get(1).getKey(), idOffsets.get(1).getValue());
    base *= idOffsets.get(1).getKey();
    for (int i = 2; i < idOffsets.size(); i++) {
      time = solve(time, base, idOffsets.get(i).getKey(), idOffsets.get(i).getValue());
      base *= idOffsets.get(i).getKey();
    }
    
    return time;
  }
  
  static long solve(long time, long base1, long base2, long ofs) {
    return LongStream.iterate(0, l -> l + 1)
        .filter(idx -> (time + (idx * base1) + ofs) % base2 == 0)
        .map(idx -> time + (idx * base1))
        .findFirst()
        .orElseThrow();
  }
}
