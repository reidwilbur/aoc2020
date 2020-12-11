package com.wilb0t.aoc;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day10 {

  static int getJoltDiffNum(List<Integer> input) {
    var sorted = Stream.concat(Stream.of(0), input.stream().sorted()).collect(Collectors.toList());

    var diffs =
        IntStream.range(1, sorted.size())
            .map(idx -> sorted.get(idx) - sorted.get(idx - 1))
            .boxed()
            .collect(Collectors.toList());

    var diff3 = (int) diffs.stream().filter(diff -> diff == 3).count() + 1;
    var diff1 = (int) diffs.stream().filter(diff -> diff == 1).count();

    return diff3 * diff1;
  }

  static long getValidCombos(List<Integer> input) {
    var sorted =
        Stream.concat(input.stream().sorted(Comparator.reverseOrder()), Stream.of(0))
            .collect(Collectors.toList());

    var pluggable =
        IntStream.range(0, sorted.size() - 1)
            .mapToObj(
                idx -> {
                  var cur = sorted.get(idx);
                  var combs =
                      sorted.subList(idx + 1, sorted.size()).stream()
                          .takeWhile(el -> cur - el <= 3)
                          .collect(Collectors.toList());
                  return Map.entry(cur, combs);
                })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    var counts = new HashMap<Integer, Long>();
    return getCombos(counts, sorted.get(0), pluggable);
  }

  static long getCombos(Map<Integer, Long> count, int plug, Map<Integer, List<Integer>> pluggable) {
    if (count.containsKey(plug)) {
      return count.get(plug);
    } else {
      if (pluggable.containsKey(plug)) {
        var combs = 0L;
        for (var np : pluggable.get(plug)) {
          combs += getCombos(count, np, pluggable);
        }
        count.put(plug, combs);
        return combs;
      } else {
        return 1;
      }
    }
  }
}
