package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day20 {

  private static final List<String> MONSTER_IMG = List.of(
      "..................#.",
      "#....##....##....###",
      ".#..#..#..#..#..#...");

  enum Side { TP, RT, BT, LT }
  
  static record Edges(Set<Tile> up, Set<Tile> rt, Set<Tile> bt, Set<Tile> lt) { 
    public boolean isCorner() {
      return Stream.of(up.size(), rt.size(), bt.size(), lt.size())
          .filter(size -> size == 0)
          .count() == 2;
    }
  }
  
  static record Tile(int id, List<String> data) {
    static Tile from(List<String> input) {
      var idStr = input.get(0).split(" ")[1];
      var id = Integer.parseInt(idStr.substring(0, idStr.length() - 1));
      var data = 
          input.subList(1, input.size()).stream()
              .map(line -> {
                var d = line.strip();
                assert d.length() == 10;
                assert d.matches("[.#]+");
                return d;
              })
              .filter(Predicate.not(String::isEmpty))
              .collect(Collectors.toList());
      assert data.size() == 10;
      return new Tile(id, data);
    }
    
    public List<String> getImgData() {
      var noTopBot = data.subList(1, data.size() - 1);
      return noTopBot.stream()
          .map(line -> line.substring(1, line.length() - 1))
          .collect(Collectors.toList());
    }
    
    public int width() {
      return data.get(0).length();
    }
    
    public Tile flipH() {
      return new Tile(id, Day20.flipH(data));
    }
    
    public Tile flipV() {
      var fdata = IntStream.range(0, data.size())
          .mapToObj(idx -> data.get(data.size() - 1 - idx))
          .collect(Collectors.toList());
      return new Tile(id, fdata);
    }

    public Tile rotateCw90() {
      return new Tile(id, Day20.rotateCw90(data));
    }
    
    public boolean matchesLeft(Tile t) {
      return IntStream.range(0, data.size())
          .allMatch(idx -> this.data.get(idx).charAt(0) == t.data.get(idx).charAt(t.width() - 1));
    }
    
    public boolean matchesRight(Tile t) {
      return IntStream.range(0, data.size())
          .allMatch(idx -> this.data.get(idx).charAt(width() - 1) == t.data.get(idx).charAt(0));
    }
    
    public boolean matchesTop(Tile t) {
      return IntStream.range(0, data.size())
          .allMatch(idx -> this.data.get(0).equals(t.data.get(t.data.size() - 1)));
    }
    
    public boolean matchesBottom(Tile t) {
      return IntStream.range(0, data.size())
          .allMatch(idx -> this.data.get(data.size() - 1).equals(t.data.get(0)));
    }
    
    public Set<Tile> findMatches(Side side, Collection<Tile> tiles) {
      Predicate<Tile> matcher = switch (side) {
        case TP -> this::matchesTop;
        case RT -> this::matchesRight;
        case BT -> this::matchesBottom;
        case LT -> this::matchesLeft;
      };

      return tiles.stream().flatMap(t -> findMatch(matcher, t)).collect(Collectors.toSet());
    }

    public Stream<Tile> findMatch(Predicate<Tile> matcher, Tile t) {
      var testTile = t;
      for (int i = 0; i < 8; i++) {
        if (matcher.test(testTile)) {
          return Stream.of(testTile);
        }
        testTile = (i == 3) ? t.flipH() : testTile.rotateCw90();
      }
      return Stream.empty();
    }
  }
  
  public static Map<Integer, Tile> parseTiles(List<String> input) {
    var starts = 
        IntStream.range(0, input.size())
            .boxed()
            .filter(idx -> input.get(idx).startsWith("Tile"))
            .collect(Collectors.toList());
    
    return starts.stream()
        .map(sidx -> Tile.from(input.subList(sidx, sidx + 11)))
        .collect(Collectors.toMap(Tile::id, Function.identity()));
  }
  
  public static Map<Integer, Edges> getEdges(Map<Integer, Tile> tiles) {
    return tiles.values().stream()
        .map(t -> {
          var others = tiles.values().stream().filter(tt -> tt.id != t.id).collect(Collectors.toList());
          var up = t.findMatches(Side.TP, others);
          var rt = t.findMatches(Side.RT, others);
          var bt = t.findMatches(Side.BT, others);
          var lt = t.findMatches(Side.LT, others);
          assert up.size() < 2;
          assert rt.size() < 2;
          assert bt.size() < 2;
          assert lt.size() < 2;
          return Map.entry(t.id, new Edges(up, rt, bt, lt));
        })
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
  
  public static long getCornerMult(Map<Integer, Tile> tiles) {
    var edgeMap = getEdges(tiles);

    return edgeMap.entrySet().stream()
        .filter(ee -> ee.getValue().isCorner())
        .map(Map.Entry::getKey)
        .mapToLong(id -> (long) id)
        .reduce((l, r) -> l * r)
        .orElseThrow();
  }
  
  public static List<List<Tile>> toMap(Map<Integer, Tile> tiles) {
    var workingSet = new HashMap<>(tiles);
    Deque<Deque<Tile>> rows = new ArrayDeque<>();
    
    var tile = tiles.values().stream().findFirst().orElseThrow();
    workingSet.remove(tile.id);
    var rowDeq = new ArrayDeque<Tile>();
    rowDeq.addFirst(tile);
    var rowDone = false;
     
    while (!rowDone) {
      var lefts = rowDeq.getFirst().findMatches(Side.LT, workingSet.values());
      assert lefts.size() < 2;
      lefts.forEach(left -> {
        workingSet.remove(left.id);
        rowDeq.addFirst(left);
      });
        
      var rights = rowDeq.getLast().findMatches(Side.RT, workingSet.values());
      assert rights.size() < 2;
      rights.forEach(right -> {
        workingSet.remove(right.id);
        rowDeq.addLast(right);
      });
        
      if (lefts.size() == 0 && rights.size() == 0) {
        rowDone = true;
      }
    }
    rows.add(rowDeq);
   
    var upDone = false;
    while (!upDone) {
      var head = rows.getFirst();
      
      var newrowUp = head.stream()
          .flatMap(tt -> { 
            var ups = tt.findMatches(Side.TP, workingSet.values());
            assert ups.size() < 2;
            return ups.stream().findFirst().stream()
                .map(t -> {
                  workingSet.remove(t.id);
                  return t;
                });
          })
          .collect(Collectors.toCollection(ArrayDeque::new));
      assert newrowUp.size() == 0 || newrowUp.size() == head.size();
      
      if (newrowUp.size() == 0) {
        upDone = true;
      } else {
        rows.addFirst(newrowUp);
      }
    }

    var dnDone = false;
    while (!dnDone) {
      var last = rows.getLast();

      var newrowDn = last.stream()
          .flatMap(tt -> {
            var dns = tt.findMatches(Side.BT, workingSet.values());
            assert dns.size() < 2;
            return dns.stream().findFirst().stream()
                .map(t -> {
                  workingSet.remove(t.id);
                  return t;
                });
          })
          .collect(Collectors.toCollection(ArrayDeque::new));
      assert newrowDn.size() == 0 || newrowDn.size() == last.size();

      if (newrowDn.size() == 0) {
        dnDone = true;
      } else {
        rows.addLast(newrowDn);
      }
    }
    
    var size = rows.getFirst().size();
    return rows.stream()
        .map(r -> {
          assert r.size() == size;
          return new ArrayList<>(r);
        }).collect(Collectors.toList());
  }
  
  static List<String> flipH(List<String> input) {
    return input.stream()
        .map(line -> new StringBuilder(line).reverse().toString())
        .collect(Collectors.toList());
  }
  
  static List<String> rotateCw90(List<String> input) {
    var width = input.get(0).length();
    var rotdata =
        IntStream.range(0, width)
            .mapToObj(i -> new StringBuilder())
            .collect(Collectors.toCollection(ArrayList::new));
    for (int r = 0; r < input.size(); r++) {
      for (int c = 0; c < width; c++) {
        rotdata.get(r).append((char) input.get(input.size() - 1 - c).charAt(r));
      }
    }
    return rotdata.stream().map(StringBuilder::toString).collect(Collectors.toList());
  }
  
  static List<String> toImage(List<List<Tile>> tiles) {
    return tiles.stream()
        .map(row -> row.stream().map(Tile::getImgData).collect(Collectors.toList()))
        .flatMap(imgrow -> {
          var lines = new ArrayList<String>();
          for (int row = 0; row < imgrow.get(0).size(); row++) {
            var sb = new StringBuilder();
            for (var tileimg: imgrow) {
              sb.append(tileimg.get(row));
            }
            lines.add(sb.toString());
          }
          return lines.stream();
        })
        .collect(Collectors.toList());
  }
  
  static int findMonsters(List<String> img) {
    var imgwidth = img.get(0).length();
    var imgStr = String.join("", img);
    var monsterStr = MONSTER_IMG.stream()
        .map(line -> String.format("%s%s", line, ".".repeat(imgwidth - line.length())))
        .collect(Collectors.joining());
    return (int) IntStream.range(0, imgStr.length() - monsterStr.length())
        .filter(idx -> IntStream.range(0, monsterStr.length())
            .filter(midx -> monsterStr.charAt(midx) == '#')
            .allMatch(midx -> monsterStr.charAt(midx) == imgStr.charAt(idx + midx)))
        .count();
  }

  public static int getMonsterCount(List<List<Tile>> tiles) {
    var img = toImage(tiles);
    var mcounts = new ArrayList<Integer>();
    for (int i = 0; i < 8; i++) {
      mcounts.add(findMonsters(img));
      img = (i == 3) ? flipH(toImage(tiles)) : rotateCw90(img);
    }
    return mcounts.stream().max(Comparator.naturalOrder()).orElseThrow();
  }
  
  public static long getRoughness(List<List<Tile>> tiles) {
    var mcount = getMonsterCount(tiles);
    var img = toImage(tiles);
    var waveCount = img.stream()
        .map(line -> line.chars().filter(c -> c == '#').count())
        .reduce(Long::sum)
        .orElseThrow();
    var monsterHashCount = MONSTER_IMG.stream()
        .map(line -> line.chars().filter(c -> c =='#').count())
        .reduce(Long::sum)
        .orElseThrow();
    return waveCount - (mcount * monsterHashCount);
  }
  
  public static String printRow(List<Tile> row) {
    var bldr = new StringBuilder();
    row.forEach(t -> bldr.append(String.format("%10s", t.id)).append(" "));
    bldr.append("\n");
    for (int r = 0; r < row.get(0).data.size(); r++) {
      for (int d = 0; d < row.size(); d++) {
        bldr.append(row.get(d).data.get(r)).append(" ");
      }
      bldr.append("\n"); 
    }
    bldr.append("\n");
    return bldr.toString();
  }
}