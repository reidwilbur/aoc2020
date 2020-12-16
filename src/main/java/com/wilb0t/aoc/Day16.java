package com.wilb0t.aoc;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day16 {
  
  static record Rule(String name, int lo, int hi) { 
    public boolean test(int val) {
      return (val >= lo) && (val <= hi);
    }
  }
  
  static record Notes(Map<String, List<Rule>> rules, List<Integer> ticket, List<List<Integer>> otherTickets) {
    public static Notes from(List<String> input) {
      var separators = IntStream.range(0, input.size())
          .filter(idx -> input.get(idx).isEmpty())
          .boxed()
          .collect(Collectors.toList());
      
      var ruleLines = input.subList(0, separators.get(0));
      var ticketLine = input.get(separators.get(1) - 1);
      var otherLines = input.subList(separators.get(1) + 2, input.size());
      
      var rules = ruleLines.stream()
          .map(line -> {
            var parts = line.split(":");
            var ruleName = parts[0];
            var rangeParts = parts[1].split(" or ");
            var ranges = Arrays.stream(rangeParts)
                .map(str -> {
                  var numParts= str.split("-");
                  return new Rule(ruleName, Integer.parseInt(numParts[0].strip()), Integer.parseInt(numParts[1].strip()));
                })
                .collect(Collectors.toList());
            return Map.entry(ruleName, ranges);
          })
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      
      var ticket = Arrays.stream(ticketLine.split(","))
          .map(Integer::parseInt)
          .collect(Collectors.toList());
      
      var otherTickets = otherLines.stream()
          .map(line -> 
            Arrays.stream(line.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList())
          )
          .collect(Collectors.toList());
      
      return new Notes(rules, ticket, otherTickets);
    }
  }

  public static long getScanningErrorRate(List<String> input) {
    var notes = Notes.from(input);
    var invalidValues = notes.otherTickets.stream()
        .flatMap(values -> 
          values.stream()
              .filter(val -> notes.rules.values().stream().flatMap(List::stream).noneMatch(r -> r.test(val)))
        )
        .collect(Collectors.toList());
    return invalidValues.stream().reduce(Integer::sum).orElseThrow();
  }

  public static long getDepartureValue(List<String> input) {
    var notes = Notes.from(input);
    var validTickets= notes.otherTickets.stream()
        .filter(values ->
            values.stream()
                .allMatch(val -> notes.rules.values().stream().flatMap(List::stream).anyMatch(r -> r.test(val)))
        )
        .collect(Collectors.toList());
    var cols = notes.ticket.size();
    
    var colRules = IntStream.range(0, cols)
        .mapToObj(idx -> {
          var colVals = validTickets.stream().map(vals -> vals.get(idx)).collect(Collectors.toList());
          var matchingRules = colVals.stream()
              .map(val -> 
                  notes.rules.values().stream()
                      .flatMap(List::stream)
                      .filter(r -> r.test(val))
                      .map(Rule::name)
                      .collect(Collectors.toSet())
              )
              .reduce(Sets::intersection)
              .orElseThrow();
          return Map.entry(idx, matchingRules);
        })
        .sorted(Comparator.comparing(e -> e.getValue().size()))
        .collect(Collectors.toList());
    
    var ruleNames = new HashSet<>(notes.rules.keySet());
    
    var colToRule = colRules.stream()
        .map(entry -> {
          if (entry.getValue().size() == 1) {
            ruleNames.removeAll(entry.getValue());
            return Map.entry(entry.getKey(), entry.getValue().stream().findFirst().orElseThrow());
          } else {
            var intersect = Sets.intersection(entry.getValue(), ruleNames).immutableCopy();
            if (intersect.size() == 1) {
              ruleNames.removeAll(intersect);
              return Map.entry(entry.getKey(), intersect.stream().findFirst().orElseThrow());
            }
            throw new RuntimeException(entry.toString());
          }
        })
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    
    return colToRule.entrySet().stream()
        .filter(e -> e.getValue().startsWith("departure"))
        .mapToLong(e -> notes.ticket.get(e.getKey()))
        .reduce((l, r) -> l * r)
        .orElseThrow();
  }
}
