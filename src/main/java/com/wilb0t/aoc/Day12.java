package com.wilb0t.aoc;

import java.util.List;

class Day12 {
  
  enum Dir {
    N, E, S, W;
    
    public Dir turn(char lr, int val) {
      int count = val / 90;
      if (lr == 'R') {
        return Dir.values()[(this.ordinal() + count) % Dir.values().length];
      } else {
        return Dir.values()[(Dir.values().length + this.ordinal() - count) % Dir.values().length];
      }
    }
  }
  
  static class ShipState {
    int x = 0;
    int y = 0;
    Dir dir = Dir.E;
    
    void update(char instr, int val) {
      switch (instr) {
        case 'L', 'R' -> dir = dir.turn(instr, val);
        case 'F' -> move(dir, val);
        case 'N' -> move(Dir.N, val);
        case 'E' -> move(Dir.E, val);
        case 'S' -> move(Dir.S, val);
        case 'W' -> move(Dir.W, val);
      }
    }
    
    void move(Dir dir, int dist) {
      switch (dir) {
        case N -> y += dist;
        case E -> x += dist;
        case S -> y -= dist;
        case W -> x -= dist;
      }
    }
  }
  
  static class ShipStateWaypoint {
    int x = 0;
    int y = 0;
    
    int wx = 10;
    int wy = 1;
    
    void update(char instr, int val) {
      switch (instr) {
        case 'F' -> { x += wx * val; y += wy * val; }
        case 'N' -> wy += val;
        case 'E' -> wx += val;
        case 'S' -> wy -= val;
        case 'W' -> wx -= val;
        case 'R' -> {
          var count = val / 90;
          for (int i = 0; i < count; i++) {
            var tmp = wx;
            wx = wy;
            wy = -tmp;
          }
        }
        case 'L' -> {
          var count = val / 90;
          for (int i = 0; i < count; i++) {
            var tmp = wx;
            wx = -wy;
            wy = tmp;
          }
        }
      }
    }
  }

  public static int getDist(List<String> input) {
    var state = new ShipState();

    input.forEach(line -> {
      var instr = line.charAt(0);
      var val = Integer.parseInt(line.substring(1));
      state.update(instr, val);
    });
    
    return Math.abs(state.x) + Math.abs(state.y);
  }
  
  public static int getDistWaypoint(List<String> input) {
    var state = new ShipStateWaypoint();

    input.forEach(line -> {
      var instr = line.charAt(0);
      var val = Integer.parseInt(line.substring(1));
      state.update(instr, val);
    });

    return Math.abs(state.x) + Math.abs(state.y);
  }
}
