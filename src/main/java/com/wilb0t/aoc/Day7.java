package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Day7 {
  
  public static record BagCount(String bagName, int count) { };
  
  static Map<String, Set<String>> bagToContainers(List<String> input) {
    var btc = new HashMap<String, Set<String>>();
    for (var line : input) {
      var containParts = line.split(" contain ");
      var pbag = containParts[0].replace(" bags", "");
      var cbagParts = containParts[1].split(", ");
      for (var cbag : cbagParts) {
        if (cbag.equals("no other bags.")) {
          continue;
        }
        var parts = cbag.split(" ");
        var bagname = parts[1] + " " + parts[2];
        
        var pbags = btc.getOrDefault(bagname, new HashSet<>());
        pbags.add(pbag);
        btc.put(bagname, pbags);
      }
    }
    return btc;
  }
  
  static Map<String, Set<BagCount>> bagToContained(List<String> input) {
    var btc = new HashMap<String, Set<BagCount>>();
    for (var line : input) {
      var containParts = line.split(" contain ");
      var pbag = containParts[0].replace(" bags", "");
      var cbagParts = containParts[1].split(", ");
      for (var cbag : cbagParts) {
        if (cbag.equals("no other bags.")) {
          continue;
        }
        var parts = cbag.split(" ");
        var bagcount = Integer.parseInt(parts[0]);
        var bagname = parts[1] + " " + parts[2];

        var cbags= btc.getOrDefault(pbag, new HashSet<>());
        cbags.add(new BagCount(bagname, bagcount));
        btc.put(pbag, cbags);
      }
    }
    return btc;
  }

  public static Set<String> getBagsThatContain(List<String> input, String bagName) {
    var btc = bagToContainers(input);
    var queue = new ArrayDeque<>(btc.get(bagName));
    var pbags = new HashSet<String>();
    while (!queue.isEmpty()) {
      var pbag = queue.pop();
      if (btc.containsKey(pbag)) {
        queue.addAll(btc.get(pbag));
      }
      pbags.add(pbag);
    }
    return pbags;
  }
  
  public static int getContainedBagCount(List<String> input, String bagName) {
    var btc = bagToContained(input);
    return getContainedBagCount(btc, bagName);
  }
  
  static int getContainedBagCount(Map<String, Set<BagCount>> input, String bagName) {
    if (input.containsKey(bagName)) {
      var bags = input.get(bagName);
      var count = 0;
      for (var bc : bags) {
        count += bc.count * (getContainedBagCount(input, bc.bagName) + 1);
      }
      return count;
    }
    return 0;
  }
}