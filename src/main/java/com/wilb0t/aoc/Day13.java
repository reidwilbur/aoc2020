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
  
  public static long getTimestamp(String input) {
    var splitInput= Arrays.stream(input.split(","))
        .collect(Collectors.toList());

    var idOffsets = IntStream.range(0, splitInput.size())
        .boxed()
        .filter(Predicate.not(idx -> splitInput.get(idx).equals("x")))
        .map(idx -> Map.entry(Integer.parseInt(splitInput.get(idx)), idx))
        .collect(Collectors.toList());

    long timestamp = 0;
    long windowSize = 1;
    for (var idOffset : idOffsets) {
      var base = idOffset.getKey();
      var ofs = idOffset.getValue();
      timestamp = findNextTimestampMatch(timestamp, windowSize, base, ofs);
      windowSize *= idOffset.getKey();
    }
    
    return timestamp;
  }
  
  static long findNextTimestampMatch(long curtime, long windowSize, long base, long ofs) {
    return LongStream.iterate(0, l -> l + 1)
        .flatMap(idx -> {
          var timestamp = curtime + (idx * windowSize);
          return ((timestamp + ofs) % base == 0)
              ? LongStream.of(timestamp)
              : LongStream.empty();
        })
        .findFirst()
        .orElseThrow();
  }
}
