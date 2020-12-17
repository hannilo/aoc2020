package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day17.ConwayCube3D
import ee.hannilo.adventofcode.day17.ConwayCube4D
import ee.hannilo.adventofcode.day17.PocketDimension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class Day17Test {

  private val input = Util.readInputLines("Day17.txt")

  private val testInput = """
    .#.
    ..#
    ###
  """.trimIndent().lines()

  //SOLUTIONS
  @Test
  fun validatePart1() {
    val dimension = PocketDimension(PocketDimension.parse3d(testInput)) { s -> ConwayCube3D(s) }
    dimension.cycle()
    assertEquals(11, dimension.cubeMap.size)
    dimension.cycle()
    assertEquals(21, dimension.cubeMap.size)
    repeat(4) {
      dimension.cycle()
    }
    assertEquals(112, dimension.cubeMap.size)
  }

  @Test
  fun part1() {
    val dimension = PocketDimension(PocketDimension.parse3d(input)) { s -> ConwayCube3D(s) }
    repeat(6) {
      dimension.cycle()
    }
    assertEquals(232, dimension.cubeMap.size)
  }

  @Test
  fun validatePart2() {
    val dimension = PocketDimension(PocketDimension.parse4d(testInput)) { s -> ConwayCube4D(s) }
    repeat(6) {
      dimension.cycle()
    }
    assertEquals(848, dimension.cubeMap.size)
  }

  @Test
  fun part2() {
    val dimension = PocketDimension(PocketDimension.parse4d(input)) { s -> ConwayCube4D(s) }
    repeat(6) {
      dimension.cycle()
    }
    assertEquals(1620, dimension.cubeMap.size)
  }
  //END SOLUTIONS

  @Test
  fun parsesInput() {
    val dimension = PocketDimension(PocketDimension.parse3d(testInput)) { s -> ConwayCube3D(s) }
    assertEquals(5, dimension.cubeMap.size)
    assertTrue(dimension.cubeMap.any { (_, c) -> c.key == "1,0,0" })
  }
}
