package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day9 {

  public static List<Long> findXMASWeakness(int preambleLen, List<Long> input) {
    var preamble = new ArrayList<>(input.subList(0, preambleLen));
    return IntStream.range(preambleLen, input.size())
        .boxed()
        .flatMap(
            idx -> {
              var nextVal = input.get(idx);
              var matches =
                  matchesPreamble(preamble, nextVal) ? Stream.<Long>empty() : Stream.of(nextVal);
              preamble.remove(0);
              preamble.add(nextVal);
              return matches;
            })
        .collect(Collectors.toList());
  }

  static boolean matchesPreamble(List<Long> preamble, Long nextVal) {
    return IntStream.range(0, preamble.size() - 1)
        .anyMatch(
            idx -> {
              var tgtval = nextVal - preamble.get(idx);
              return preamble.subList(idx + 1, preamble.size()).contains(tgtval);
            });
  }

  static Long findWeaknessRange(Long weakVal, List<Long> input) {
    var range =
        IntStream.range(0, input.size() - 1)
            .boxed()
            .flatMap(
                idx -> {
                  var weakacc = weakVal;
                  var sbidx = idx;
                  while (weakacc > 0 && sbidx < input.size()) {
                    weakacc -= input.get(sbidx);
                    sbidx += 1;
                  }
                  return (weakacc == 0 && sbidx - idx > 1) 
                      ? Stream.of(input.subList(idx, sbidx)) 
                      : Stream.empty();
                })
            .findFirst()
            .orElseThrow();

    var stats = range.stream().mapToLong(Long::longValue).summaryStatistics();
    return stats.getMin() + stats.getMax();
  }
}
