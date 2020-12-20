package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day20.Tile
import ee.hannilo.adventofcode.day20.TileArrangement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//TODO part2, not that hard, just merge the tiles and search the mutations with regex or smth
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
    val corners = lists.map { list -> list.filterIndexed {index, _ ->  listOf(0, 2, 6, 8).contains(index) } }
    val res = corners.map { it.fold(1L) {acc, tile -> acc * tile.baseId().toLong() } }
    println(res)
    assertEquals(20899048083289, res.first())
  }

  @Test
  fun part1() {
    val arrangement = TileArrangement(input)
    val lists = arrangement.search()
    println(lists)
    val corners = lists.map { list -> list.filterIndexed {index, _ ->  listOf(0, 11, 132, 143).contains(index) } }
    val res = corners.map { it.fold(1L) {acc, tile -> acc * tile.baseId().toLong() } }
    println(res)

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
