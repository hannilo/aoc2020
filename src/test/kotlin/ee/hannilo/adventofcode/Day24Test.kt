package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day24.HexTile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day24Test {

  private val testInput = """
    sesenwnenenewseeswwswswwnenewsewsw
    neeenesenwnwwswnenewnwwsewnenwseswesw
    seswneswswsenwwnwse
    nwnwneseeswswnenewneswwnewseswneseene
    swweswneswnenwsewnwneneseenw
    eesenwseswswnenwswnwnwsewwnwsene
    sewnenenenesenwsewnenwwwse
    wenwwweseeeweswwwnwwe
    wsweesenenewnwwnwsenewsenwwsesesenwne
    neeswseenwwswnwswswnw
    nenwswwsewswnenenewsenwsenwnesesenew
    enewnwewneswsewnwswenweswnenwsenwsw
    sweneswneswneneenwnewenewwneswswnese
    swwesenesewenwneswnwwneseswwne
    enesenwswwswneneswsenwnewswseenwsese
    wnwnesenesenenwwnenwsewesewsesesew
    nenewswnwewswnenesenwnesewesw
    eneswnwswnwsenenwnwnwwseeswneewsenese
    neswnwewnwnwseenwseesewsenwsweewe
    wseweeenwnesenwwwswnew
  """.trimIndent().lines()

  private val input = Util.readInputLines("Day24.txt")

  @Test
  fun regex() {
    val match = "([ns]?[ew])".toRegex().findAll("sesenwnenenewseeswwswswwnenewsewsw").map { it.groupValues[0] }.toList()
    println(match)
  }

  @Test
  fun parses() {
    val direction = HexTile.parse("esenee")
    assertEquals("Hex(3,-3,0)", direction.toString())
  }

  @Test
  fun normalizesCycle() {
    val direction = HexTile.parse("wsenewsene")
    assertEquals("Hex(0,0,0)", direction.toString())
  }

  @Test
  fun validatePart1() {
    val tiles = testInput.map(HexTile.Companion::parse).groupBy { it.toString() }
    assertEquals(10, tiles.filter { it.value.size % 2 == 1 }.size)
  }

  @Test
  fun part1() {
    val tiles = input.map(HexTile.Companion::parse).groupBy { it.toString() }
    assertEquals(438, tiles.filter { it.value.size % 2 == 1 }.size)
  }

  @Test
  fun neighbors() {
    val e = HexTile(0, 0, 0)
    val neighbors = e.getNeighbors()
    val expected = listOf(
      "Hex(1,0,-1)",
      "Hex(1,-1,0)",
      "Hex(0,-1,1)",
      "Hex(-1,0,1)",
      "Hex(-1,1,0)",
      "Hex(0,1,-1)",
    )
    assertEquals(expected, neighbors.map { it.toString() })
  }

  fun solvePart2(list: List<String>): Int {
    val tiles = list.map(HexTile.Companion::parse).groupBy { it }
    var black = tiles.filter { it.value.size % 2 == 1 }.keys.toSet()
    repeat(100) { day ->
      val remainBlack =
        black.map { b -> b to b.getNeighbors().filter { black.contains(it) } }.filter { (_, n) -> n.size in 1..2 }.toMap()
      val turnBlack = black.flatMap { it.getNeighbors() }.distinct()
        .filter { !black.contains(it) }
        .map { n -> n to n.getNeighbors().filter { black.contains(it) } }
        .filter { (t, n) -> n.size == 2 }.toMap()
      black = remainBlack.keys + turnBlack.keys
    }
    return black.size
  }

  @Test
  fun validatePart2() {
    assertEquals(2208, solvePart2(testInput))
  }

  @Test
  fun part2() {
    assertEquals(4038, solvePart2(input))
  }
}
