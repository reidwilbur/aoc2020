package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day21 {
  
  public static Map<String, List<Set<String>>> toAllergenMap(List<String> input) {
    return input.stream()
        .flatMap(line -> {
          var parts = line.split(" \\(contains ");
          var igs = Set.of(parts[0].split(" "));
          var alls = 
              List.of(parts[1].substring(0, parts[1].length() - 1)
                  .replace(",", "").split(" "));
          return alls.stream().map(all -> Map.entry(all, igs));
        })
        .collect(
            Collectors.groupingBy(
                Map.Entry::getKey, 
                Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
  }
  
  public static List<String> getNonAllergic(Map<String, List<Set<String>>> allMap) {
    return List.of();
  }
}
