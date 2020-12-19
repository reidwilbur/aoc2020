package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day18 {
  
  interface Token { 
    String rest();
  }
  
  static record Number(long num, String rest) implements Token {
    static final Pattern NUMBER = Pattern.compile("^\\s*(\\d+)\\s*");
    
    static Optional<Token> matches(String input) {
      var matcher = NUMBER.matcher(input);
      if (matcher.find()) {
        return Optional.of(new Number(Long.parseLong(matcher.group(1)), input.substring(matcher.end())));
      }
      return Optional.empty();
    }
  }
  
  static record Op(String op, String rest) implements Token {
    static final String ADD = "+";
    static final String MUL = "*";
    static final Pattern OP = Pattern.compile("^\\s*([*+])\\s+");

    static Optional<Token> matches(String input) {
      var matcher = OP.matcher(input);
      if (matcher.find()) {
        return Optional.of(new Op(matcher.group(1), input.substring(matcher.end())));
      }
      return Optional.empty();
    }
  }
  
  static record SubExpr(String expr, String rest) implements Token { 
    static Optional<Token> matches(String input) {
      if (input.startsWith("(")) {
        var stack = new ArrayDeque<>(List.of('('));
        var idx = 1;
        while (!stack.isEmpty() && idx < input.length()) {
          if (input.charAt(idx) == '(') {
            stack.push(input.charAt(idx));
          } else if (input.charAt(idx) == ')') {
            stack.pop();
          }
          idx += 1;
        }
        if (stack.isEmpty()) {
          var rest = (idx + 1 < input.length()) ? input.substring(idx + 1) : "";
          return Optional.of(new SubExpr(input.substring(1, idx - 1), rest));
        } else {
          throw new RuntimeException("Invalid input: parens unbalanced " + input);
        }
      }
      return Optional.empty(); 
    }
  }
  
  static Optional<Token> nextToken(String input) {
      if (input.isEmpty()) {
        return Optional.empty();
      } else {
        return Number.matches(input)
            .or(() -> Op.matches(input))
            .or(() -> SubExpr.matches(input));
      }
  }

  public static List<Long> eval(List<String> input) {
    return input.stream().map(Day18::eval).collect(Collectors.toList());
  }
  
  public static List<Long> evalPlusPlus(List<String> input) {
    return input.stream().map(Day18::evalPlusPlus).collect(Collectors.toList());
  }

  public static long eval(String input) {
    var leftToken = nextToken(input).orElseThrow();
    var val = getTokenVal(leftToken);
    var rest = leftToken.rest();
    
    while (!rest.isEmpty()) {
      var opToken = nextToken(rest).orElseThrow();
      var rightToken= nextToken(opToken.rest()).orElseThrow();

      if (opToken instanceof Op op) {
        long rightVal = getTokenVal(rightToken);
        val = switch (op.op) {
          case Op.MUL -> val * rightVal;
          case Op.ADD -> val + rightVal;
          default -> throw new RuntimeException("Invalid input" + op);
        };
      } else {
        throw new RuntimeException("Invalid input " + opToken);
      }

      rest = rightToken.rest();
    }
    return val;
  }

  static long getTokenVal(Token token) {
    if (token instanceof Number num) {
      return num.num;
    } else if (token instanceof SubExpr subExpr) {
      return eval(subExpr.expr);
    } else {
      throw new RuntimeException("Invalid input " + token);
    }
  }

  public static long evalPlusPlus(String input) {
    var leftToken = nextToken(input).orElseThrow();
    long val = getTokeValPlusPlus(leftToken);
    var rest = leftToken.rest();
    
    while (!rest.isEmpty()) {
      var opToken = nextToken(rest).orElseThrow();

      if (opToken instanceof Op op) {
        if (Op.ADD.equals(op.op)) {
          var rightToken = nextToken(op.rest).orElseThrow();
          val += getTokeValPlusPlus(rightToken);
          rest = rightToken.rest();
        } else {
          val *= evalPlusPlus(op.rest);
          rest = "";
        }
      } else {
        throw new RuntimeException("Invalid input " + opToken);
      }
    }
    return val;
  }
  
  static long getTokeValPlusPlus(Token token) {
    if (token instanceof Number num) {
      return num.num;
    } else if (token instanceof SubExpr subExpr) {
      return evalPlusPlus(subExpr.expr);
    } else {
      throw new RuntimeException("Invalid input " + token);
    }
  }
}
