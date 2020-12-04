package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Day4 {
  
  public static record FieldValidator(String field, Predicate<String> validator) { 
    public boolean validate(Passport p) {
      return validator.test(getFieldValue(p));
    }
    
    private String getFieldValue(Passport p) {
      return p.fields.getOrDefault(field, "");
    }
  }
  
  public static record Passport(Map<String, String> fields) {
    private static final List<FieldValidator> validators =
        List.of(
            new FieldValidator("byr", fv -> fv.matches("^19[2-9][0-9]|200[0-2]$")),
            new FieldValidator("iyr", fv -> fv.matches("^201[0-9]|2020$")),
            new FieldValidator("eyr", fv -> fv.matches("^202[0-9]|2030$")),
            new FieldValidator("hgt", fv -> fv.matches("^1[5-8][0-9]cm|19[0-3]cm|59in|6[0-9]in|7[0-6]in$")),
            new FieldValidator("hcl", fv -> fv.matches("^#[0-9a-f]{6}$")),
            new FieldValidator("ecl", fv -> fv.matches("^amb|blu|brn|gry|grn|hzl|oth$")),
            new FieldValidator("pid", fv -> fv.matches("^[0-9]{9}$"))
        );
    
    public boolean hasReqFields() {
      return fields.keySet().containsAll(validators.stream().map(FieldValidator::field).collect(Collectors.toSet()));
    }
    
    public boolean isValid() {
      return validators.stream().allMatch(fv -> fv.validate(this));
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
