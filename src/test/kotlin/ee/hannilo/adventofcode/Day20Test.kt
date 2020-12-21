package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day20.Tile
import ee.hannilo.adventofcode.day20.TileArrangement
import ee.hannilo.adventofcode.day20.TileArrangement.Companion.MONSTER_BOT
import ee.hannilo.adventofcode.day20.TileArrangement.Companion.MONSTER_MID
import ee.hannilo.adventofcode.day20.TileArrangement.Companion.MONSTER_TOP
import ee.hannilo.adventofcode.day20.TileArrangement.Companion.mutations
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day20Test {

  val tilestr = """
      Tile 1111:
      ##########
      #.........
      #.........
      #.........
      #.........
      #.........
      #.........
      #.........
      #.........
      #.........
    """.trimIndent().lines()

  val testinput = Util.readInputLines("Day20Test.txt").let { Util.chunkListByBlanks(it) }.map { Tile.fromInput(it) }

  val input = Util.readInputLines("Day20.txt").let { Util.chunkListByBlanks(it) }.map { Tile.fromInput(it) }

  @Test
  fun validatePart1() {
    val arrangement = TileArrangement(testinput)
    val lists = arrangement.search()
    println(lists)
    val corners = lists.map { list -> list.filterIndexed { index, _ -> listOf(0, 2, 6, 8).contains(index) } }
    val res = corners.map { it.fold(1L) { acc, tile -> acc * tile.baseId().toLong() } }
    println(res)
    assertEquals(20899048083289, res.first())
  }

  @Test
  fun part1() {
    val arrangement = TileArrangement(input)
    val lists = arrangement.search()
    println(lists)
    val corners = lists.map { list -> list.filterIndexed { index, _ -> listOf(0, 11, 132, 143).contains(index) } }
    val res = corners.map { it.fold(1L) { acc, tile -> acc * tile.baseId().toLong() } }
    println(res)
    assertEquals(29125888761511, res[0])
  }

  @Test
  fun validatePart2() {
    val arrangement = TileArrangement(testinput)
    val list = arrangement.search()[0]
    println(list)
    val merged = TileArrangement.merge(list).rotL().flipY()
    assertEquals(
      """
      .####...#####..#...###..
      #####..#..#.#.####..#.#.
      .#.#...#.###...#.##.O#..
      #.O.##.OO#.#.OO.##.OOO##
      ..#O.#O#.O##O..O.#O##.##
      ...#.#..##.##...#..#..##
      #.##.#..#.#..#..##.#.#..
      .###.##.....#...###.#...
      #.####.#.#....##.#..#.#.
      ##...#..#....#..#...####
      ..#.##...###..#.#####..#
      ....#.##.#.#####....#...
      ..##.##.###.....#.##..#.
      #...#...###..####....##.
      .#.##...#.##.#.#.###...#
      #.###.#..####...##..#...
      #.###...#.##...#.##O###.
      .O##.#OO.###OO##..OOO##.
      ..O#.O..O..O.#O##O##.###
      #.#..##.########..#..##.
      #.#####..#.#...##..#....
      #....##..#.#########..##
      #...#.....#..##...###.##
      #..###....##.#...##.##.#
    """.trimIndent().replace('O', '#'), merged.toDisplay()
    )
    val count = TileArrangement.findMonsters(merged)
    assertEquals(2, count)
    val totalPounds = merged.rows.reduce(String::plus).count { it == '#' }
    assertEquals(273, totalPounds - count * 15)
  }

  @Test
  fun part2() {
    val arrangement = TileArrangement(input)
    val list = arrangement.search()[0]
    val merged = TileArrangement.merge(list)
    println(merged.toDisplay())
    val maxMonsters = mutations(merged).map { it to TileArrangement.findMonsters(it) }.maxByOrNull { (_, count) -> count }!!
    println(maxMonsters)
    assertEquals(15, maxMonsters.second)
    val totalPounds = maxMonsters.first.rows.fold(0) {acc, s -> acc + s.count { it == '#'} }
    assertEquals(2219, totalPounds - maxMonsters.second * 15)
  }

  @Test
  fun monsterRegex() {
    val monster = """
.#.#...#.###...#.##.O#..
#.O.##.OO#.#.OO.##.OOO##
..#O.#O#.O##O..O.#O##.##
    """.trimIndent().replace('O', '#').lines()
    assertTrue(MONSTER_BOT.containsMatchIn(monster[2]))
    assertTrue(MONSTER_MID.containsMatchIn(monster[1]))
    assertTrue(MONSTER_TOP.containsMatchIn(monster[0]))
  }

  @Test
  fun mergesInner() {
    val arrangement = TileArrangement(testinput)
    val list = arrangement.search()[0]
    println(list)
    val merged = TileArrangement.merge(list)
    println(merged.toDisplay())
    assertEquals(
      """
      .#.#..#.##...#.##..#####
      ###....#.#....#..#......
      ##.##.###.#.#..######...
      ###.#####...#.#####.#..#
      ##.#....#.##.####...#.##
      ...########.#....#####.#
      ....#..#...##..#.#.###..
      .####...#..#.....#......
      #..#.##..#..###.#.##....
      #.####..#.####.#.#.###..
      ###.#.#...#.######.#..##
      #.####....##..########.#
      ##..##.#...#...#.#.#.#..
      ...#..#..#.#.##..###.###
      .#.#....#.##.#...###.##.
      ###.#...#..#.##.######..
      .#.#.###.##.##.#..#.##..
      .####.###.#...###.#..#.#
      ..#.#..#..#.#.#.####.###
      #..####...#.#.#.###.###.
      #####..#####...###....##
      #.##..#..#...#..####...#
      .#.###..##..##..####.##.
      ...###...##...#...#..###
    """.trimIndent(), merged.toDisplay()
    )
  }

  @Test
  fun generatesInnerTile() {
    val tilestr = """
      Tile 0:
      .....
      .###.
      .###.
      .###.
      .....
    """.trimIndent().lines()

    val outer = Tile.fromInput(tilestr)
    val inner = outer.inner()

    assertEquals(
      """
      ###
      ###
      ###
    """.trimIndent(), inner.toDisplay()
    )
  }

  @Test
  fun parses() {
    val tile = Tile.fromInput(tilestr)
    assertEquals("1111", tile.id)
    assertEquals("#.........", tile.lastRow())
    assertEquals("#.........", tile.lastCol())
    assertEquals("##########", tile.firstCol())
    assertEquals("##########", tile.firstRow())
  }

  @Test
  fun flipsTile() {
    val tile = Tile(tilestr.drop(1))
    println(tile)
    println()

    assertEquals("##########", tile.row(0))
    assertEquals("##########", tile.col(0))

    val flipX = tile.flipX()
    assertEquals(".........#", flipX.row(1))

    val flipY = tile.flipY()
    println(flipY)
    assertEquals("##########", flipY.row(9))
  }

  @Test
  fun rotatesTile() {
    val tile = Tile(tilestr.drop(1))
    println(tile)
    println()
    assertEquals("##########", tile.row(0))

    val rotR = tile.rotR()
    println(rotR)
    println()
    assertEquals("##########", rotR.row(0))
    assertEquals("#.........", rotR.col(0))

    val rotL = tile.rotL()
    println(rotL)
    assertEquals("#.........", rotL.row(0))
  }

}
