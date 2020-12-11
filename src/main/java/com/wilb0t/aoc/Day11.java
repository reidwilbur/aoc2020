package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.checkerframework.checker.units.qual.A;

class Day11 {
  
  static record Coord(int r, int c) { }
  
  enum State { 
    FLOOR, EMPTY, FULL;
    
    State nextState(List<State> neighbors) {
      return switch (this) {
        case FLOOR -> FLOOR;
        case EMPTY -> neighbors.stream().filter(n -> !n.equals(FLOOR)).allMatch(EMPTY::equals) ? FULL : EMPTY;
        case FULL -> neighbors.stream().filter(n -> !n.equals(FLOOR)).filter(FULL::equals).count() >= 4 ? EMPTY : FULL;
      };
    }
    
    State nextStateTolerant(List<State> neighbors) {
      return switch (this) {
        case FLOOR -> FLOOR;
        case EMPTY -> neighbors.stream().filter(n -> !n.equals(FLOOR)).allMatch(EMPTY::equals) ? FULL : EMPTY;
        case FULL -> neighbors.stream().filter(n -> !n.equals(FLOOR)).filter(FULL::equals).count() >= 5 ? EMPTY : FULL;
      };
    }
    
    static State fromChar(char c) {
      return switch (c) {
          case '.' -> State.FLOOR;
          case 'L' -> State.EMPTY;
          case '#' -> State.FULL;
          default -> throw new RuntimeException("innvalid char " + c);
      };
    }
    
    String print() {
      return switch (this) {
        case FLOOR -> ".";
        case EMPTY -> "L";
        case FULL -> "#";
      };
    }
  };

  public static int getSeatCount(List<String> input, boolean part2) {
    var gameOfSeats = parse(input);
    var width = gameOfSeats.get(0).size();
    
    var changed = true;
    while (changed) {
      changed = false;
      var nextGame = new ArrayList<List<State>>();
      for (int r = 0; r < gameOfSeats.size(); r++) {
        var line = new ArrayList<State>();
        for (int c = 0; c < width; c++) {
          var coord = new Coord(r, c);
          var neighbors = 
              (part2) 
                  ? getNeighborsBySight(gameOfSeats, coord, gameOfSeats.size(), width) 
                  : getNeighbors(gameOfSeats, coord, gameOfSeats.size(), width);
          var state = gameOfSeats.get(r).get(c);
          var nextState = 
              (part2) 
                  ? state.nextStateTolerant(neighbors)
                  : state.nextState(neighbors);
          changed = changed || !nextState.equals(state);
          line.add(nextState);
        }
        nextGame.add(line);
      }
      gameOfSeats = nextGame;
    }
    
    return gameOfSeats.stream()
        .map(line -> line.stream().filter(el -> el.equals(State.FULL)).count())
        .reduce(Long::sum)
        .orElseThrow()
        .intValue();
  }
  
  static void printGame(List<List<State>> game) {
    var sb = new StringBuilder();
    for (var line : game) {
      for (var state : line) {
        sb.append(state.print());
      }
      sb.append("\n");
    }
    System.out.println(sb);
  }
  
  static List<State> getNeighbors(List<List<State>> game, Coord cur, int rows, int cols) {
    return Stream.of(
        new Coord(cur.r - 1, cur.c - 1), new Coord(cur.r - 1, cur.c), new Coord(cur.r - 1, cur.c + 1),
        new Coord(cur.r, cur.c - 1), new Coord(cur.r, cur.c + 1),
        new Coord(cur.r + 1, cur.c - 1), new Coord(cur.r + 1, cur.c), new Coord(cur.r + 1, cur.c + 1))
        .filter(coord -> coord.r >= 0 && coord.r < rows && coord.c >= 0 && coord.c < cols)
        .map(coord -> game.get(coord.r).get(coord.c))
        .collect(Collectors.toList());
  }

  static List<State> getNeighborsBySight(List<List<State>> game, Coord cur, int rows, int cols) {
    Predicate<Coord> inBounds = (coord) -> coord.r >= 0 && coord.r < rows && coord.c >= 0 && coord.c < cols;
    Predicate<Coord> notFloor = (coord) -> !game.get(coord.r).get(coord.c).equals(State.FLOOR);
    var nw = IntStream.range(1, Math.max(rows, cols))
        .mapToObj(idx -> new Coord(cur.r - idx, cur.c -idx))
        .filter(inBounds)
        .filter(notFloor)
        .findFirst();
    var n = IntStream.range(1, Math.max(rows, cols))
        .mapToObj(idx -> new Coord(cur.r - idx, cur.c))
        .filter(inBounds)
        .filter(notFloor)
        .findFirst();
    var ne = IntStream.range(1, Math.max(rows, cols))
        .mapToObj(idx -> new Coord(cur.r - idx, cur.c + idx))
        .filter(inBounds)
        .filter(notFloor)
        .findFirst();
    var e = IntStream.range(1, Math.max(rows, cols))
        .mapToObj(idx -> new Coord(cur.r, cur.c + idx))
        .filter(inBounds)
        .filter(notFloor)
        .findFirst();
    var se = IntStream.range(1, Math.max(rows, cols))
        .mapToObj(idx -> new Coord(cur.r + idx, cur.c + idx))
        .filter(inBounds)
        .filter(notFloor)
        .findFirst();
    var s = IntStream.range(1, Math.max(rows, cols))
        .mapToObj(idx -> new Coord(cur.r + idx, cur.c))
        .filter(inBounds)
        .filter(notFloor)
        .findFirst();
    var sw = IntStream.range(1, Math.max(rows, cols))
        .mapToObj(idx -> new Coord(cur.r + idx, cur.c - idx))
        .filter(inBounds)
        .filter(notFloor)
        .findFirst();
    var w = IntStream.range(1, Math.max(rows, cols))
        .mapToObj(idx -> new Coord(cur.r, cur.c - idx))
        .filter(inBounds)
        .filter(notFloor)
        .findFirst();
    return Stream.of(nw, n, ne, e, se, s, sw, w)
        .flatMap(Optional::stream)
        .map(coord -> game.get(coord.r).get(coord.c))
        .collect(Collectors.toList());
  }

  static List<List<State>> parse(List<String> input) {
    return input.stream()
        .map(line -> line.chars().mapToObj(i -> State.fromChar((char)i)).collect(Collectors.toList()))
        .collect(Collectors.toList());
  }
}
