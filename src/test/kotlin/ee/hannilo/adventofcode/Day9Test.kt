package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.Util.Companion.readInputFile
import ee.hannilo.adventofcode.day9.Day9
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day9Test {

  private val input = readInputFile("Day9.txt").map { it.toLong() }

  private val testInput = """
    35
    20
    15
    25
    47
    40
    62
    55
    65
    95
    102
    117
    150
    182
    127
    219
    299
    277
    309
    576
  """.trimIndent().lines().map { it.toLong() }

  //SOLUTIONS
  @Test
  fun validatePart1() {
    val solver = Day9(testInput, memSize = 5)
    Assertions.assertEquals(127, testInput[solver.load()])
  }

  @Test
  fun part1() {
    val solver = Day9(input, memSize = 25)
    Assertions.assertEquals(31161678, input[solver.load()])

  }

  @Test
  fun validatePart2() {
    val window = Day9.findContiguousSum(127, testInput)
    Assertions.assertEquals(62, window.minOrNull()!! + window.maxOrNull()!!)
  }

  @Test
  fun part2() {
    val window = Day9.findContiguousSum(31161678, input)
    println(window)
    Assertions.assertEquals(5453868, window.minOrNull()!! + window.maxOrNull()!!)
  }
  //END SOLUTIONS

  @Test
  fun buildsTrees() {
    val solver = Day9(testInput.take(5))
    Assertions.assertEquals(5, solver.trees.size)
  }

  @Test
  fun failsIfInvalid() {
    val solver = Day9(testInput.take(5))
    Assertions.assertFalse(solver.next(30))
  }

  @Test
  fun addsIfValid() {
    val solver = Day9(testInput.take(5))
    Assertions.assertTrue(solver.next(testInput[5]))
  }
}
