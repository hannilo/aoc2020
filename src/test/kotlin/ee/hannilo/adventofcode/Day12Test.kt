package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day12.Ferry
import ee.hannilo.adventofcode.day12.Heading
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue

class Day12Test {

  private val input = Util.readInputLines("Day12.txt")

  private val testInput = """
    F10
    N3
    F7
    R90
    F11
  """.trimIndent().lines()

  @Test
  fun validatePart1() {
    val ferry = Ferry()
    ferry.nextPosition(testInput[0])
    assertEquals(Pair(10, 0), ferry.position)
    assertEquals(Heading.E, ferry.heading)

    ferry.nextPosition(testInput[1])
    assertEquals(Pair(10, 3), ferry.position)
    assertEquals(Heading.E, ferry.heading)

    ferry.nextPosition(testInput[2])
    assertEquals(Pair(17, 3), ferry.position)
    assertEquals(Heading.E, ferry.heading)

    ferry.nextPosition(testInput[3])
    assertEquals(Pair(17, 3), ferry.position)
    assertEquals(Heading.S, ferry.heading)

    ferry.nextPosition(testInput[4])
    assertEquals(Pair(17, -8), ferry.position)
    assertEquals(Heading.S, ferry.heading)

    assertEquals(25, ferry.position.first.absoluteValue + ferry.position.second.absoluteValue)
  }

  @Test
  fun part1() {
    val ferry = Ferry()
    input.forEach {
      println(it)
      ferry.nextPosition(it)
    }
    assertEquals(562, ferry.position.first.absoluteValue + ferry.position.second.absoluteValue)
  }

  @Test
  fun validatePart2() {
    val ferry = Ferry()
    ferry.nextWaypoint(testInput[0])
    assertEquals(Pair(100, 10), ferry.position)
    assertEquals(Pair(10, 1), ferry.waypoint)

    ferry.nextWaypoint(testInput[1])
    assertEquals(Pair(100, 10), ferry.position)
    assertEquals(Pair(10, 4), ferry.waypoint)

    ferry.nextWaypoint(testInput[2])
    assertEquals(Pair(170, 38), ferry.position)
    assertEquals(Pair(10, 4), ferry.waypoint)

    ferry.nextWaypoint(testInput[3])
    assertEquals(Pair(170, 38), ferry.position)
    assertEquals(Pair(4, -10), ferry.waypoint)

    ferry.nextWaypoint(testInput[4])
    assertEquals(Pair(214, -72), ferry.position)
    assertEquals(Pair(4, -10), ferry.waypoint)
  }

  @Test
  fun part2() {
    val ferry = Ferry()
    input.forEach {
      println(it)
      ferry.nextWaypoint(it)
    }
    assertEquals(101860, ferry.position.first.absoluteValue + ferry.position.second.absoluteValue)
  }
}
