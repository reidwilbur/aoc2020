package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Day4 {
  
  public static record Passport(Map<String, String> fields) { 
    private static final Map<String, Predicate<Passport>> fieldValidators =
        Map.of(
            "byr", 
            (p) -> p.field("byr").matches("^19[2-9][0-9]|200[0-2]$"),
            "iyr", 
            (p) -> p.field("iyr").matches("^201[0-9]|2020$"),
            "eyr", 
            (p) -> p.field("eyr").matches("^202[0-9]|2030$"),
            "hgt", 
            (p) -> p.field("hgt").matches("^1[5-8][0-9]cm|19[0-3]cm|59in|6[0-9]in|7[0-6]in$"),
            "hcl", 
            (p) -> p.field("hcl").matches("^#[0-9a-f]{6}$"),
            "ecl", 
            (p) -> p.field("ecl").matches("^amb|blu|brn|gry|grn|hzl|oth$"),
            "pid", 
            (p) -> p.field("pid").matches("^[0-9]{9}$")
        );
    
    private String field(String name) {
      return fields.getOrDefault(name, "");
    }
    
    public boolean hasReqFields() {
      return fields.keySet().containsAll(fieldValidators.keySet());
    }
    
    public boolean isValid() {
      return fieldValidators.values().stream().allMatch(p -> p.test(this));
    }
    
    public static Passport fromString(String line) {
      var parts = line.split(" ");
      var fields = new HashMap<String, String>();
      for (var part : parts) {
        var fieldParts = part.split(":");
        fields.put(fieldParts[0], fieldParts[1]);
      }
      return new Passport(fields);
    }
  }

  public static List<Passport> toPassports(List<String> input) {
    var concats = new ArrayList<String>();
    var idx = 0;
    for (var line : input) {
      if (line.isEmpty()) {
        idx += 1;
      } else {
        if (concats.size() == idx) {
          concats.add(line.strip());
        } else {
          concats.set(idx, concats.get(idx) + " " + line.strip());
        }
      }
    }
    return concats.stream().map(Passport::fromString).collect(Collectors.toList());
  }
  
  public static List<Passport> getValidPassports(List<String> input) {
    return toPassports(input).stream()
        .filter(Passport::hasReqFields)
        .collect(Collectors.toList());
  }
  
  public static List<Passport> getValidatedPassports(List<String> input) {
    return toPassports(input).stream()
        .filter(Passport::isValid)
        .collect(Collectors.toList());
  }
}
