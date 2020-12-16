package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day15 {

  public static int getNumber(List<Integer> input, int turn) {
    if (turn <= input.size()) {
      return input.get(turn - 1);
    }
    
    var numTolastSpoken = 
        IntStream.rangeClosed(1, input.size())
          .mapToObj(t -> Map.entry(input.get(t - 1), (List<Integer>)new ArrayList<>(List.of(t))))
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    
    var lastSpoken = input.get(input.size() - 1);
    for (int t = input.size() + 1; t <= turn; t++) {
      var lastSpokenList= numTolastSpoken.get(lastSpoken);
      if (lastSpokenList.size() == 1) {
        var spoken = 0;
        updateMap(numTolastSpoken, spoken, t);
        lastSpoken = spoken;
      } else {
        var spoken = lastSpokenList.get(1) - lastSpokenList.get(0);
        updateMap(numTolastSpoken, spoken, t);
        lastSpoken = spoken;
      }
    }
        
    return lastSpoken;
  }
  
  static void updateMap(Map<Integer, List<Integer>> numToLastSpoken, int spoken, int turn) {
    var lastSpokenList = numToLastSpoken.getOrDefault(spoken, new ArrayList<>());
    if (lastSpokenList.size() == 2) {
      lastSpokenList.set(0, lastSpokenList.get(1));
      lastSpokenList.set(1, turn);
    } else if (lastSpokenList.size() == 1){
      lastSpokenList.add(turn);
    } else {
      lastSpokenList.add(turn);
      numToLastSpoken.put(spoken, lastSpokenList);
    }
  }
}
