package ee.hannilo.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {

  private val input = Util.readInputFile("Day10.txt").map { it.toInt() }.sorted()

  val testInput = """
    28
    33
    18
    42
    31
    14
    46
    20
    48
    47
    24
    23
    49
    45
    19
    38
    39
    11
    1
    32
    25
    35
    8
    17
    7
    9
    4
    2
    34
    10
    3
    """.trimIndent().lines().map { it.toInt() }.sorted()


  fun findDiffs(list: List<Int>, diff: Int): Int {
    return list.also { println(it) }.windowed(2, 1).also { println(it) }
      .filter { it.size == 2 }
      .map { it to it.last() - it.first() }
      .filter { it.second == diff }
      .also { println(it) }
      .size
  }

  fun findOptions(list: List<Int>): Long {
    val pathsFrom = mutableMapOf<Int, Long>()
    return list.windowed(4, 1, true).reversed().also { println(it) }
      .map { l ->
        l.first() to l.drop(1).filter { it - l.first() <= 3 }
      }.fold(0) { _, pair: Pair<Int, List<Int>> -> //fold abuse
        pathsFrom[pair.first] = pair.second
          .fold(0) { a, i ->
            a + pathsFrom.getOrDefault(i, 1).coerceAtLeast(1)
          }
        println(pathsFrom)
        pathsFrom[pair.first]!!
      }
  }


  //SOLUTIONS
  @Test
  fun validatePart1() {
    val list = listOf(0) + testInput + listOf(testInput.last() + 3)
    val diff1 = findDiffs(list, 1)
    val diff3 = findDiffs(list, 3)
    assertEquals(22, diff1)
    assertEquals(10, diff3)
  }

  @Test
  fun part1() {
    val list = listOf(0) + input + listOf(input.last() + 3)
    val diff1 = findDiffs(list, 1)
    val diff3 = findDiffs(list, 3)
    assertEquals(2664, diff1 * diff3)
  }

  @Test
  fun validatePart2() {
    val list = listOf(0) + testInput + listOf(testInput.last() + 3)
    val options = findOptions(list)
    assertEquals(19208, options)
  }

  @Test
  fun part2() {
    val list = listOf(0) + input + listOf(input.last() + 3)
    val options = findOptions(list)
    assertEquals(148098383347712, options)
  }
  //END SOLUTIONS

}
