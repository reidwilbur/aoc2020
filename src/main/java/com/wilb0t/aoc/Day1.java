package com.wilb0t.aoc;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day1 {

  static Optional<Integer> getSumMult(int sum, List<Integer> entries) {
    var entrySet = Set.copyOf(entries);

    return entries.stream()
        .flatMap(e -> entrySet.contains(sum - e) ? Stream.of(e * (sum - e)) : Stream.empty())
        .findFirst();
  }

  static Optional<Integer> getTripleSumMult(int sum, List<Integer> entries) {
    var entrySet = Set.copyOf(entries);

    return IntStream.range(0, entries.size() - 3)
        .boxed()
        .flatMap(
            idx -> {
              var e1 = entries.get(idx);
              var subSum = sum - e1;
              var subList = entries.subList(idx + 1, entries.size());
              return subList.stream()
                  .flatMap(
                      e2 ->
                          entrySet.contains(subSum - e2)
                              ? Stream.of(e1 * e2 * (subSum - e2))
                              : Stream.empty());
            })
        .findFirst();
  }
}
