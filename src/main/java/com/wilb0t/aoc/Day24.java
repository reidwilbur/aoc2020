package com.wilb0t.aoc;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24 {

  // using cube coords from this awesome guide
  // https://www.redblobgames.com/grids/hexagons/#coordinates-cube
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

    public Coords(int q, int r, int s) {
      this.q = q;
      this.r = r;
      this.s = s;
    }

    public Set<Coords> neighbors() {
      return Set.of(
          new Coords(q + 1, r, s - 1),
          new Coords(q + 1, r - 1, s),
          new Coords(q, r - 1, s + 1),
          new Coords(q - 1, r, s + 1),
          new Coords(q - 1, r + 1, s),
          new Coords(q, r + 1, s - 1));
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

    static Coords fromPath(String path) {
      var idx = 0;
      var coords = new Coords(0, 0, 0);
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
          throw new RuntimeException(
              String.format("Bad path at ids %d : %s", idx, path.substring(idx)));
        }
      }
      return coords;
    }
  }

  public static int getBlackTileCount(List<String> paths) {
    var blackTiles = new HashSet<Coords>();
    for (var path : paths) {
      var coords = Coords.fromPath(path);
      if (blackTiles.contains(coords)) {
        blackTiles.remove(coords);
      } else {
        blackTiles.add(coords);
      }
    }
    return blackTiles.size();
  }

  public static int getGolTileCount(List<String> paths, int steps) {
    Set<Coords> blackTiles = new HashSet<>();
    for (var path : paths) {
      var coords = Coords.fromPath(path);
      if (blackTiles.contains(coords)) {
        blackTiles.remove(coords);
      } else {
        blackTiles.add(coords);
      }
    }

    for (var step = 0; step < steps; step++) {
      var coords =
          blackTiles.stream()
              .flatMap(c -> Stream.concat(c.neighbors().stream(), Stream.of(c)))
              .collect(Collectors.toSet());
      var nextBlackTiles = new HashSet<Coords>();
      for (var coord : coords) {
        var blackNbors = Sets.intersection(coord.neighbors(), blackTiles).size();
        if (blackTiles.contains(coord) && (blackNbors == 1 || blackNbors == 2)) {
          nextBlackTiles.add(coord);
        } else if (blackNbors == 2) {
          nextBlackTiles.add(coord);
        }
      }
      blackTiles = nextBlackTiles;
    }

    return blackTiles.size();
  }
}
