package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day8 {
  
  public static record State(int ip, int acc) { }

  public enum Op { ACC, JMP, NOP }

  public static record Instr(Op op, int val) {

    public static Instr fromString(String line) {
      var parts = line.split(" ");
      return new Instr(Op.valueOf(parts[0].toUpperCase(Locale.ROOT)), Integer.parseInt(parts[1]));
    }
    
    public Instr swap() {
      return switch (op) {
        case ACC -> this;
        case JMP -> new Instr(Op.NOP, val);
        case NOP -> new Instr(Op.JMP, val);
      };
    }
  }

  public static State execAndHaltOnRepeat(List<Instr> instrs) {
    var ip = 0;
    var acc = 0;
    
    var seenIps = new HashSet<Integer>();
    while (!seenIps.contains(ip) && ip < instrs.size()) {
      var instr = instrs.get(ip);
      seenIps.add(ip);
      switch (instr.op) {
        case ACC -> {
          acc += instr.val;
          ip += 1;
        }
        case JMP -> ip += instr.val;
        case NOP -> ip += 1;
        default -> throw new RuntimeException("unknown instr " + instr);
      }
    }
    return new State(ip, acc);
  }
  
  public static int getAccBeforeRepeat(List<String> input) {
    var instrs = input.stream().map(Instr::fromString).collect(Collectors.toList());
    var state = execAndHaltOnRepeat(instrs);
    return state.acc;
  }
  
  public static int getAccWithFix(List<String> input) {
    var instrs = input.stream().map(Instr::fromString).collect(Collectors.toList());

    var changeable = Set.of(Op.JMP, Op.NOP);
    var idxToChange = IntStream.range(0, input.size())
        .filter(idx -> changeable.contains(instrs.get(idx).op))
        .boxed()
        .collect(Collectors.toList());
    
    for (var idx : idxToChange) {
      var instrsSwapped = new ArrayList<>(instrs);
      instrsSwapped.set(idx, instrsSwapped.get(idx).swap());
      
      var state = execAndHaltOnRepeat(instrsSwapped);
      if (state.ip >= instrsSwapped.size()) {
        return state.acc;
      }
    }
    throw new RuntimeException("Could not find non-repeating code");
  }
}