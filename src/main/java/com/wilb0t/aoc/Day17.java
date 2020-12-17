package com.wilb0t.aoc;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day17 {
  
  static record Point(int x, int y, int z) { 
    public Set<Point> getNeighbors() {
      var neighbors = new HashSet<Point>();
      for (int zz = z - 1; zz <= z + 1; zz++) {
        for (int yy = y - 1; yy <= y + 1; yy++) {
          for (int xx = x - 1; xx <= x + 1; xx++) {
            if (!(xx == x && yy == y && zz == z)) {
              neighbors.add(new Point(xx, yy, zz));
            }
          }
        }
      }
      return neighbors;
    }
  }
  
  static record Point4(int x, int y, int z, int w) {
    public Set<Point4> getNeighbors() {
      var neighbors = new HashSet<Point4>();
      for (int ww = w -1; ww <= w + 1; ww++) { 
        for (int zz = z - 1; zz <= z + 1; zz++) {
          for (int yy = y - 1; yy <= y + 1; yy++) {
            for (int xx = x - 1; xx <= x + 1; xx++) {
              if (!(xx == x && yy == y && zz == z && ww == w)) {
                neighbors.add(new Point4(xx, yy, zz, ww));
              }
            }
          }
        }
      }
      return neighbors;
    }
  }

  public static int simulate(List<String> input, int steps) {
    var points = new HashSet<Point>();
    for (int y = 0; y < input.size(); y++) {
      for (int x = 0; x < input.get(0).length(); x++) {
        if (input.get(y).charAt(x) == '#') {
          points.add(new Point(x, y, 0));
        }
      }
    }
    
    for (int idx = 0; idx < steps; idx++) {
      var toCheck = points.stream()
          .flatMap(p -> Stream.concat(Stream.of(p), p.getNeighbors().stream()))
          .collect(Collectors.toSet());
      
      var nextPoints = new HashSet<Point>();
      for (var pt : toCheck) {
            var nbrs = pt.getNeighbors();
            var activeNbrs = Sets.intersection(points, nbrs).size();
            if ((points.contains(pt) && (activeNbrs == 2 || activeNbrs == 3))
                || (!points.contains(pt) && activeNbrs == 3)) {
              nextPoints.add(pt);
            } 
      }
      
      points = nextPoints;
    }
    
    return points.size();
  }

  public static int simulate4(List<String> input, int steps) {
    var points = new HashSet<Point4>();
    for (int y = 0; y < input.size(); y++) {
      for (int x = 0; x < input.get(0).length(); x++) {
        if (input.get(y).charAt(x) == '#') {
          points.add(new Point4(x, y, 0, 0));
        }
      }
    }

    for (int idx = 0; idx < steps; idx++) {
      var toCheck = points.stream()
          .flatMap(p -> Stream.concat(Stream.of(p), p.getNeighbors().stream()))
          .collect(Collectors.toSet());

      var nextPoints = new HashSet<Point4>();
      for (var pt : toCheck) {
        var nbrs = pt.getNeighbors();
        var activeNbrs = Sets.intersection(points, nbrs).size();
        if ((points.contains(pt) && (activeNbrs == 2 || activeNbrs == 3))
            || (!points.contains(pt) && activeNbrs == 3)) {
          nextPoints.add(pt);
        }
      }

      points = nextPoints;
    }

    return points.size();
  }
}
