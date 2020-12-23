package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day19 {

  static record TestResult(boolean matches, int len) { }

  interface Rule {
    int id();
    
    TestResult test(String ind, Map<Integer, Rule> rules, int idx, String str);
    
    static Rule from(String input) {
      var parts = input.split(": ");
      var id = Integer.parseInt(parts[0]);
     
      if (parts[1].startsWith("\"")) {
        return new CharRule(id, parts[1].charAt(1));
      } else {
        var subRuleParts = parts[1].split(" \\| ");
        var subrules = 
            Arrays.stream(subRuleParts)
                .map(
                    subrulePart -> 
                        Arrays.stream(subrulePart.strip().split(" "))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return new CompoundRule(id, subrules);
      }
    }
  }
  
  static record CharRule(int id, char c) implements Rule {
    @Override
    public TestResult test(String ind, Map<Integer, Rule> rules, int idx, String str) {
      if (idx < str.length()) {
        return new TestResult(str.charAt(idx) == c, 1);
      } else {
        return new TestResult(false, 0);
      }
    }
  }
  
  static record CompoundRule(int id, List<List<Integer>> subrules) implements Rule {
    @Override
    public TestResult test(String ind, Map<Integer, Rule> rules, int idx, String str) {
//      for (var ruleIds: subrules) {
//        var result = test(ind, rules, ruleIds, idx, str);
//        if (result.matches) {
//          return result;
//        }
//      }
      var matched = subrules.stream()
          .map(ruleIds -> test(ind, rules, ruleIds, idx, str))
          .filter(TestResult::matches)
          .sorted(Comparator.comparing(TestResult::len).reversed())
          .collect(Collectors.toList());
      
      if (matched.size() == 0) {
        return new TestResult(false, 0);
      } else {
        return matched.get(0);
      }
    }
    
    TestResult test(String ind, Map<Integer, Rule> rules, List<Integer> ruleIds, int idx, String str) {
      var iidx = idx;
      for (var ruleId : ruleIds) {
        if (iidx >= str.length()) {
          return new TestResult(false, 0);
        }
        //System.out.println(ind + "  Eval rule " + rules.get(ruleId) + " iidx " + iidx + " str " + str.substring(idx));
        var result = rules.get(ruleId).test(ind + "  ", rules, iidx, str);
        if (result.matches) {
          //System.out.println(ind + "  Rule " + ruleId + " passed");
          iidx += result.len;
        } else {
          //System.out.println(ind + "  Rule " + ruleId + " failed");
          return new TestResult(false, 0);
        }
      }
      //System.out.println(ind + "Rule " + this.id + " passed");
      return new TestResult(true, iidx - idx);
    }
  }
  
  public static long getMatchCount(List<String> input, int ruleId) {
    var rules = 
        input.stream()
            .filter(line -> line.matches("^\\d+: .+"))
        .map(Rule::from)
        .collect(Collectors.toMap(Rule::id, Function.identity()));
    
    var msgLines =
        input.stream()
            .filter(line -> line.matches("^[ab].+"))
            .collect(Collectors.toList());
    
    var matched = msgLines.stream()
        .filter(msg -> {
          var result = rules.get(ruleId).test("", rules, 0, msg);
          return result.matches && result.len == msg.length();
        })
        .collect(Collectors.toList());
    
    return matched.size();
  }

  public static List<String> getMatchesPatched(List<String> input, int ruleId) {
    var rules = new HashMap<>(
        input.stream()
            .filter(line -> line.matches("^\\d+: .+"))
            .map(Rule::from)
            .collect(Collectors.toMap(Rule::id, Function.identity())));
    
    rules.put(8, Rule.from("8: 42 | 42 42 | 42 42 42 | 42 42 42 42 | 42 42 42 42 42 | 42 42 42 42 42 42 | 42 42 42 42 42 42 42 | 42 42 42 42 42 42 42 42 | 42 42 42 42 42 42 42 42 42 | 42 42 42 42 42 42 42 42 42 42"));
    rules.put(11, Rule.from("11: 42 31 | 42 42 31 31 | 42 42 42 31 31 31 | 42 42 42 42 31 31 31 31 | 42 42 42 42 42 31 31 31 31 31 | 42 42 42 42 42 42 31 31 31 31 31 31 | 42 42 42 42 42 42 42 31 31 31 31 31 31 31 | 42 42 42 42 42 42 42 42 31 31 31 31 31 31 31 31"));
    //rules.put(8, Rule.from("8: 42 | 42 8"));
    //rules.put(11, Rule.from("11: 42 31 | 42 11 31"));

    var msgLines =
        input.stream()
            .filter(line -> line.matches("^[ab].+"))
            .collect(Collectors.toList());

    return msgLines.stream()
        .filter(msg -> {
          var result = rules.get(ruleId).test("", rules, 0, msg);
          return result.matches && result.len == msg.length();
        })
        .collect(Collectors.toList());
  }
}
