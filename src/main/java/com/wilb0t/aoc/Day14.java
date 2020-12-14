package com.wilb0t.aoc;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

class Day14 {
  
  interface Instr { }
  
  static record Mask(long zeroMask, long oneMask, List<Long> floaters) implements Instr { 
    public static Mask from(String line) {
      var mask = line.split(" = ")[1];
      var zeroMask = 0L;
      var oneMask = 0L;
      var floaters = new ArrayList<Long>();
      for (int idx = 0; idx < mask.length(); idx++) {
        if (mask.charAt(idx) == '1') {
          oneMask = oneMask | 1L << (mask.length() - 1L - idx);
        } else if (mask.charAt(idx) == '0') {
          zeroMask = zeroMask | 1L << (mask.length() - 1L - idx);
        } else {
          floaters.add(1L << (mask.length() - 1L -idx));
        }
      };
      return new Mask(zeroMask, oneMask, Lists.reverse(floaters));
    }
    
    public long maskedVal(long val) {
      return (val | oneMask) & ~zeroMask;
    }
    
    public List<Long> getFloatedAddresses(long address) {
      var baseAddr = (address | oneMask) | ~zeroMask;
      return IntStream.range(0, 1 << floaters.size())
          .boxed()
          .map(idx -> {
            var floatedAddr = baseAddr;
            for (int i = 0; i < floaters.size(); i++) {
              var bitIsSet = (idx & (1L << i)) != 0L;
              if (bitIsSet) {
                floatedAddr = floatedAddr | floaters.get(i);
              } else {
                floatedAddr = floatedAddr & ~floaters.get(i);
              }
            }
            return floatedAddr;
          })
          .collect(Collectors.toList());
    }
  }
  
  static record Write(long addr, long val) implements Instr {
    public static Write from(String line) {
      var parts = line.split(" = ");
      var addr = Long.parseLong(parts[0].split("[\\[\\]]")[1]);
      return new Write(addr, Long.parseLong(parts[1]));
    }    
  }

  public static long sumOfMemWithMask(List<String> input) {
    var instrs = input.stream()
        .map(line -> line.startsWith("mask") ? Mask.from(line) : Write.from(line))
        .collect(Collectors.toList());

    var mask = new Mask(0L, 0L, List.of());
    var mem = new HashMap<Long, Long>();
    for (var instr: instrs) {
      if (instr instanceof Mask newMask) {
        mask = newMask;
      } else if (instr instanceof Write write) {
        var maskedVal = mask.maskedVal(write.val);
        mem.put(write.addr, maskedVal);
      }
    }
    
    return mem.values().stream().reduce(Long::sum).orElseThrow();
  }

  public static long sumOfMemWithFloaters(List<String> input) {
    var instrs = input.stream()
        .map(line -> line.startsWith("mask") ? Mask.from(line) : Write.from(line))
        .collect(Collectors.toList());

    var mask = new Mask(0L, 0L, List.of());
    var mem = new HashMap<Long, Long>();
    for (var instr: instrs) {
      if (instr instanceof Mask newMask) {
        mask = newMask;
      } else if (instr instanceof Write write) {
        var floatedAddrs = mask.getFloatedAddresses(write.addr);
        floatedAddrs.forEach(addr -> mem.put(addr, write.val));
      }
    }

    return mem.values().stream().reduce(Long::sum).orElseThrow();
  }
}
