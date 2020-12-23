package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.Iterables;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day20Test {

  private static List<String> input1;

  private static List<String> testInput1;
  
  @BeforeAll
  static void initAll() throws Exception {
    input1 =
        Files.readAllLines(
            Path.of(Day20Test.class.getResource("/Day20_input1.txt").toURI()),
            StandardCharsets.UTF_8);
    
    testInput1 =
        Files.readAllLines(
            Path.of(Day20Test.class.getResource("/Day20_inputTest.txt").toURI()),
            StandardCharsets.UTF_8);
  }

  @BeforeEach
  void init() throws Exception {}

  @Test
  void test_rotate() {
    var tiles = Day20.parseTiles(input1);
    var tile3331 = tiles.get(3331);
    
    var tile3331_360 = tile3331.rotateCw90().rotateCw90().rotateCw90().rotateCw90();
    assertThat(tile3331_360, is(tile3331));
  }
  
  @Test
  void test_getCornerMult() {
    var tiles = Day20.parseTiles(testInput1);
    assertThat(Day20.getCornerMult(tiles), is(20899048083289L));
  }

  @Test
  void test_getCornerMult_input1() {
    var tiles = Day20.parseTiles(input1);
    var map = Day20.toMap(tiles);
    var rowsize = map.get(0).size();
    var cornerMult = (long) map.get(0).get(0).id() 
        * map.get(0).get(rowsize - 1).id()
        * map.get(map.size() - 1).get(0).id() 
        * map.get(map.size() - 1).get(rowsize - 1).id();
    assertThat(cornerMult, is(12519494280967L));
  }
  
  @Test
  void test_getMonsterCount() {
    var tiles = Day20.parseTiles(testInput1);
    var map = List.of(
        List.of(tiles.get(1951).flipV(), tiles.get(2311).flipV(), tiles.get(3079)),
        List.of(tiles.get(2729).flipV(), tiles.get(1427).flipV(), tiles.get(2473).flipH().rotateCw90()),
        List.of(tiles.get(2971).flipV(), tiles.get(1489).flipV(), tiles.get(1171).flipH())
    );
    assertThat(Day20.getMonsterCount(map), is(2));
  }
  
  @Test
  public void test_toImage() {
    var tiles = Day20.parseTiles(testInput1);
    var img1951 = List.of(
        ".####...",
        "....#..#",
        "...#####",
        "##.#....",
        "###.####",
        "##.##.##",
        "###....#",
        ".#.#..#.");
    assertThat(tiles.get(1951).getImgData(), is(img1951));
    
    var map = List.of(
        List.of(tiles.get(1951).flipV(), tiles.get(2311).flipV(), tiles.get(3079)),
        List.of(tiles.get(2729).flipV(), tiles.get(1427).flipV(), tiles.get(2473).flipH().rotateCw90()),
        List.of(tiles.get(2971).flipV(), tiles.get(1489).flipV(), tiles.get(1171).flipH())
    );
    var img = List.of(
        ".#.#..#.##...#.##..#####",
        "###....#.#....#..#......",
        "##.##.###.#.#..######...",
        "###.#####...#.#####.#..#",
        "##.#....#.##.####...#.##",
        "...########.#....#####.#",
        "....#..#...##..#.#.###..",
        ".####...#..#.....#......",
        "#..#.##..#..###.#.##....",
        "#.####..#.####.#.#.###..",
        "###.#.#...#.######.#..##",
        "#.####....##..########.#",
        "##..##.#...#...#.#.#.#..",
        "...#..#..#.#.##..###.###",
        ".#.#....#.##.#...###.##.",
        "###.#...#..#.##.######..",
        ".#.#.###.##.##.#..#.##..",
        ".####.###.#...###.#..#.#",
        "..#.#..#..#.#.#.####.###",
        "#..####...#.#.#.###.###.",
        "#####..#####...###....##",
        "#.##..#..#...#..####...#",
        ".#.###..##..##..####.##.",
        "...###...##...#...#..###");
    assertThat(Day20.toImage(map), is(img));
  }
  
  @Test
  void test_findMonsters() {
    var testImg = List.of(
        ".####...#####..#...###..",
        "#####..#..#.#.####..#.#.",
        ".#.#...#.###...#.##.##..",
        "#.#.##.###.#.##.##.#####",
        "..##.###.####..#.####.##",
        "...#.#..##.##...#..#..##",
        "#.##.#..#.#..#..##.#.#..",
        ".###.##.....#...###.#...",
        "#.####.#.#....##.#..#.#.",
        "##...#..#....#..#...####",
        "..#.##...###..#.#####..#",
        "....#.##.#.#####....#...",
        "..##.##.###.....#.##..#.",
        "#...#...###..####....##.",
        ".#.##...#.##.#.#.###...#",
        "#.###.#..####...##..#...",
        "#.###...#.##...#.######.",
        ".###.###.#######..#####.",
        "..##.#..#..#.#######.###",
        "#.#..##.########..#..##.",
        "#.#####..#.#...##..#....",
        "#....##..#.#########..##",
        "#...#.....#..##...###.##",
        "#..###....##.#...##.##.#");
    assertThat(Day20.findMonsters(testImg), is(2));
  }
  
  @Test
  void test_getRoughnes_input() {
    var tiles = Day20.parseTiles(input1);
    var map = Day20.toMap(tiles);
    var roughness = Day20.getRoughness(map);
    assertThat(roughness, is(2442L));
  }
  
  @Test
  void test_getRoughness() {
    var tiles = Day20.parseTiles(testInput1);
    var map = Day20.toMap(tiles);
    assertThat(Day20.getRoughness(map), is(273L));
  }
}
