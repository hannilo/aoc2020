package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day3.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class Day3Test {

  private val input = Util.readInputFile("Day3.txt").map { s ->
    s.trim()
  }

  private val testInput = listOf(
    "..##.......",
    "#...#...#..",
    ".#....#..#.",
    "..#.#...#.#",
    ".#...##..#.",
    "..#.##.....",
    ".#.#.#....#",
    ".#........#",
    "#.##...#...",
    "#...##....#",
    ".#..#...#.#",
  )

  // SOLUTIONS
  @Test
  fun verifyPart1() {
    val slope = TobogganSlope(3, 1)
    Assertions.assertEquals(7, Day3.countTreesOnSlope(TreeTopology(testInput), slope))
  }

  @Test
  fun part1() {
    val topo = TreeTopology(input)
    val slope = TobogganSlope(3, 1)

    val result = Day3.countTreesOnSlope(topo, slope)
    Assertions.assertEquals(211, result)
  }

  @Test
  fun verifySolution2() {
    val topo = TreeTopology(testInput)
    val slopes = listOf(
      TobogganSlope(1, 1),
      TobogganSlope(3, 1),
      TobogganSlope(5, 1),
      TobogganSlope(7, 1),
      TobogganSlope(1, 2),
    )
    val results = slopes.map {
      Day3.countTreesOnSlope(topo, it)
    }
    val expected = listOf(2, 7, 3, 4, 2)
    Assertions.assertEquals(expected, results)
    Assertions.assertEquals(336, expected.fold(1) { acc, i -> acc * i })
  }

  @Test
  fun solution2() {
    val topo = TreeTopology(input)
    val slopes = listOf(
      TobogganSlope(1, 1),
      TobogganSlope(3, 1),
      TobogganSlope(5, 1),
      TobogganSlope(7, 1),
      TobogganSlope(1, 2),
    )
    val results = slopes.map {
      Day3.countTreesOnSlope(topo, it)
    }
    val expected = listOf(67, 211, 77, 89, 37)
    Assertions.assertEquals(expected, results)
    Assertions.assertEquals(BigDecimal(3584591857), expected.fold(BigDecimal.ONE) { acc, i -> acc.multiply(BigDecimal(i)) })
  }
  //END SOLUTIONS

  @Test
  fun parsesMap() {
    println(input)
    val topo = TreeTopology(input)
    Assertions.assertEquals(323, topo.height)
    Assertions.assertEquals(31, topo.width)
  }

  @Test
  fun parseUnequalLength_throws() {
    val list = listOf(
      "....",
      "....",
      "...",
      "....",
    )
    Assertions.assertThrows(IllegalArgumentException::class.java) { TreeTopology(list) }
  }

  @Test
  fun parseInvalidSymbol_throws() {
    val list = listOf(
      "####",
      "....",
      "..#.",
      "...?",
      "....",
    )
    Assertions.assertThrows(IllegalArgumentException::class.java) { TreeTopology(list) }
  }

  @Test
  fun getsSymbolAt() {
    val idxX = 4
    val idxY = 1

    val list = listOf(
      "....",
      "#...",
      ".#..",
      "....",
    )
    val topo = TreeTopology(list)
    Assertions.assertEquals(TREE, topo.valueAt(idxX, idxY))
  }

  @Test
  fun getsSymbolFromSlope() {
    val slope = TobogganSlope(3, 1)
    val pos = TobogganSlopePosition(slope, 2)

    val list = listOf(
      "....",
      "#...",
      "..#.",
      "....",
    )
    val topo = TreeTopology(list)
    Assertions.assertEquals(TREE, topo.valueAt(pos.posX, pos.posY))
  }

  @Test
  fun countsTrees() {
    val stepX = 1
    val stepY = 1

    val list = listOf(
      "....",
      ".#..",
      ".##.",
      "..#.",
    )

    val topo = TreeTopology(list)
    Assertions.assertEquals(2, Day3.countTreesOnSlope(topo, TobogganSlope(stepX, stepY)))
  }

}
