package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.Util.Companion.chunkListByBlanks
import ee.hannilo.adventofcode.Util.Companion.readInputFile
import ee.hannilo.adventofcode.day6.Day6
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day6Test {

  val testinput = """
    abc

    a
    b
    c

    ab
    ac

    a
    a
    a
    a

    b
  """.trimIndent().lines()

  @Test
  fun verifyPart1() {
    val groups = chunkListByBlanks(testinput)
    println(groups)

    val distinctAnswers = groups.map { Day6.findDistinct(it) }
    println(distinctAnswers)

    val sum = distinctAnswers.sumBy { it.size }
    println(sum)
    Assertions.assertEquals(11, sum)
  }

  @Test
  fun part1() {
    val groups = chunkListByBlanks(readInputFile("Day6.txt"))
    println(groups)

    val distinctAnswers = groups.map { Day6.findDistinct(it) }
    println(distinctAnswers)

    val sum = distinctAnswers.sumBy { it.size }
    println(sum)

    Assertions.assertEquals(6587, sum)
  }

  @Test
  fun verifySolution2() {
    val groups = chunkListByBlanks(testinput)
    println(groups)

    val sum = groups.map { Day6.findIntersect(it) }.sumBy { it.size }
    Assertions.assertEquals(6, sum)
  }

  @Test
  fun solution2() {
    val groups = chunkListByBlanks(readInputFile("Day6.txt"))
    println(groups)

    val sum = groups.map { Day6.findIntersect(it) }.sumBy { it.size }
    println(sum)
    Assertions.assertEquals(3235, sum)
  }
}
