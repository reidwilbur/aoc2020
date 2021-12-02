package com.wilb0t.aoc;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Day24 {
  
  static class Coords {
    int q;
    int r;
    int s;
    
    void ne() {
      q += 1;
      r -= 1;
    }
    
    void nw() {
      r -= 1;
      s += 1;
    }
    
    void se() {
      r += 1;
      s -= 1;
    }
    
    void sw() {
      q -= 1;
      r += 1;
    }
    
    void e() {
      q += 1;
      s -= 1;
    }
    
    void w() {
      q -= 1;
      s += 1;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Coords coords = (Coords) o;
      return q == coords.q && r == coords.r && s == coords.s;
    }

    @Override
    public int hashCode() {
      return Objects.hash(q, r, s);
    }
  }
  
  public static int getBlackTileCount(List<String> paths) {
    var grid = new HashSet<Coords>();
    for (var path : paths) {
      var coords = toCoords(path);
      if (grid.contains(coords)) {
        grid.remove(coords);
      } else {
        grid.add(coords);
      }
    }
    return grid.size();
  }
  
  static Coords toCoords(String path) {
    var idx = 0;
    var coords = new Coords();
    var wpath = path;
    while (wpath.length() > 0) {
      if (wpath.startsWith("ne")) {
        coords.ne();
        wpath = wpath.substring(2);
      } else if (wpath.startsWith("nw")) {
        coords.nw();
        wpath = wpath.substring(2);
      } else if (wpath.startsWith("se")) {
        coords.se();
        wpath = wpath.substring(2);
      } else if (wpath.startsWith("sw")) {
        coords.sw();
        wpath = wpath.substring(2);
      } else if (wpath.startsWith("e")) {
        coords.e();
        wpath = wpath.substring(1);
      } else if (wpath.startsWith("w")) {
        coords.w();
        wpath = wpath.substring(1);
      } else {
        throw new RuntimeException(String.format("Bad path at ids %d : %s", idx, path.substring(idx)));
      }
    }
    return coords;
  }
}
