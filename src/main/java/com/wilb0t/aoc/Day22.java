package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day22 {

  public static List<List<Integer>> parseInput(List<String> input) {
    var splitIdx = IntStream.range(0, input.size())
        .filter(idx -> input.get(idx).isEmpty())
        .findFirst()
        .orElseThrow();
    
    var p1Stack = input.subList(1, splitIdx).stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    
    var p2Stack = input.subList(splitIdx + 2, input.size()).stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    
    return List.of(p1Stack, p2Stack);
  }
  
  static long getScore(List<Integer> stack) {
    return IntStream.range(0, stack.size())
        .map(idx -> (stack.size() - idx) * stack.get(idx))
        .sum();
  }
  
  public static long playCombat(List<List<Integer>> stacks) {
    var p1Stack = new ArrayDeque<>(stacks.get(0));
    var p2Stack = new ArrayDeque<>(stacks.get(1));
    
    while (!(p1Stack.isEmpty() || p2Stack.isEmpty())) {
      var p1card = p1Stack.removeFirst();
      var p2card = p2Stack.removeFirst();

      if (p1card > p2card) {
        p1Stack.addLast(p1card);
        p1Stack.addLast(p2card);
      } else if (p2card > p1card) {
        p2Stack.addLast(p2card);
        p2Stack.addLast(p1card);
      } else {
        throw new RuntimeException("Player cards same val " + p1card);
      }
    }
    
    return (p1Stack.isEmpty()) 
        ? getScore(new ArrayList<>(p2Stack))
        : getScore(new ArrayList<>(p1Stack));
  }
  
  public static long playRecurCombat(List<List<Integer>> stacks) {
    var result = playRecurCombat(
        Optional.of(new HashSet<String>()), 
        new ArrayDeque<>(stacks.get(0)), 
        new ArrayDeque<>(stacks.get(1)));
    
    return (result.get(0).isEmpty()) 
        ? getScore(new ArrayList<>(result.get(1)))
        : getScore(new ArrayList<>(result.get(0)));
  }
  
  static List<Deque<Integer>> playRecurCombat(Optional<Set<String>> prevRounds, Deque<Integer> p1Stack, Deque<Integer> p2Stack) {
    if (p1Stack.isEmpty() || p2Stack.isEmpty()) {
      return List.of(p1Stack, p2Stack);
    } else {
      while (!(p1Stack.isEmpty() || p2Stack.isEmpty())) {

        var proundCheck =
            prevRounds.flatMap(
                pr -> {
                  var roundKey =
                      p1Stack.stream().map(String::valueOf).collect(Collectors.joining(","))
                          + ":"
                          + p2Stack.stream().map(String::valueOf).collect(Collectors.joining(","));

                  if (pr.contains(roundKey)) {
                    return Optional.of(List.of(p1Stack, new ArrayDeque<Integer>()));
                  }

                  pr.add(roundKey);
                  return Optional.empty();
                });
        if (proundCheck.isPresent()) {
          return proundCheck.get();
        }

        var p1card = p1Stack.removeFirst();
        var p2card = p2Stack.removeFirst();
        
        if (p1card <= p1Stack.size() && p2card <= p2Stack.size()) {
          var p1StackR = new ArrayDeque<>(new ArrayList<>(p1Stack).subList(0, p1card));
          var p2StackR = new ArrayDeque<>(new ArrayList<>(p2Stack).subList(0, p2card));
          
          var subRes = playRecurCombat(Optional.empty(), p1StackR, p2StackR);
          if (subRes.get(1).isEmpty()) {
            p1Stack.addLast(p1card);
            p1Stack.addLast(p2card);
          } else {
            p2Stack.addLast(p2card);
            p2Stack.addLast(p1card);
          }
        } else {
          if (p1card > p2card) {
            p1Stack.addLast(p1card);
            p1Stack.addLast(p2card);
          } else if (p2card > p1card) {
            p2Stack.addLast(p2card);
            p2Stack.addLast(p1card);
          } else {
            throw new RuntimeException("Player cards same val " + p1card);
          }
        }
      }
      
      return List.of(p1Stack, p2Stack);
    }
  }
}
