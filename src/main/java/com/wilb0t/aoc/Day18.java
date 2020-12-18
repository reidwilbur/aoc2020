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
    static final Pattern NUMBER =
        Pattern.compile("^\\s*(\\d+)\\s*");
    
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
    
    static final Pattern OP =
        Pattern.compile("^\\s*([*+])\\s+");

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
        var stack = new ArrayDeque<>(List.of(0));
        var idx = 1;
        while (!stack.isEmpty() && idx < input.length()) {
          if (input.charAt(idx) == '(') {
            stack.push(idx);
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
    long val;
    var leftOpt = nextToken(input);
    if (leftOpt.isPresent()) {
      var left = leftOpt.get();
      if (left instanceof Number num) {
        val = num.num;
      } else if (left instanceof SubExpr subExpr) {
        val = eval(subExpr.expr);
      } else {
        throw new RuntimeException("Invalid input " + left);
      }
      
      var rest = left.rest();
      while (!rest.isEmpty()) {
        var opOpt = nextToken(rest);
        var rightOpt = opOpt.map(Token::rest).flatMap(Day18::nextToken);

        if (opOpt.isPresent() && rightOpt.isPresent()) {
          var op = opOpt.get();
          var right = rightOpt.get();

          String opVal;
          if (op instanceof Op opp) {
            opVal = opp.op;
          } else {
            throw new RuntimeException("Invalid input " + op);
          }

          long rightVal;
          if (right instanceof Number num) {
            rightVal = num.num;
          } else if (right instanceof SubExpr subExpr) {
            rightVal = eval(subExpr.expr);
          } else {
            throw new RuntimeException("Invalid input " + right);
          }

          val = switch (opVal) {
            case Op.MUL -> val * rightVal;
            case Op.ADD -> val + rightVal;
            default -> throw new RuntimeException("Invalid input" + op);
          };

          rest = right.rest();
        } else {
          throw new RuntimeException("Invalid input " + rest);
        }
      }
      return val;
    } else {
      throw new RuntimeException("Invalid input " + input);
    }
  }
  
  public static long evalPlusPlus(String input) {
    long val;
    var leftOpt = nextToken(input);
    if (leftOpt.isPresent()) {
      var left = leftOpt.get();
      if (left instanceof Number num) {
        val = num.num;
      } else if (left instanceof SubExpr subExpr) {
        val = evalPlusPlus(subExpr.expr);
      } else {
        throw new RuntimeException("Invalid input " + left);
      }

      var rest = left.rest();
      while (!rest.isEmpty()) {
        var opOpt = nextToken(rest);

        if (opOpt.isEmpty()) {
          throw new RuntimeException("Invalid input " + rest);
        }
        
        var token = opOpt.get();

        if (token instanceof Op op) {
          if (Op.ADD.equals(op.op)) {
            var right = nextToken(op.rest).orElseThrow();
            long rightVal;
            if (right instanceof Number num) {
              rightVal = num.num;
            } else if (right instanceof SubExpr subExpr) {
              rightVal = evalPlusPlus(subExpr.expr);
            } else {
              throw new RuntimeException("Invalid input " + right);
            }
            val += rightVal;
            rest = right.rest();
          } else {
            val *= evalPlusPlus(op.rest);
            rest = "";
          }
        } else {
          throw new RuntimeException("Invalid input " + token);
        }
      }
      return val;
    } else {
      throw new RuntimeException("Invalid input " + input);
    }
  }
}
