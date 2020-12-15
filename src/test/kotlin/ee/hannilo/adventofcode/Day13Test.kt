package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.Util.Companion.lcm
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {

  val input = Util.readInputLines("Day13.txt")

  val testInput = """
    939
    7,13,x,x,59,x,31,19
  """.trimIndent().lines()

  private fun solvePart1(input: List<String>): Int {
    val timestamp = input.first().toInt()
    val intervals = input[1].split(",").filter { it != "x" }.map { it.toInt() }
    val line = intervals.map {
      it to it - timestamp % it
    }.minByOrNull { it.second }!!
    return line.first * line.second
  }

  private fun parsePart2(input: List<String>): List<Pair<Long, Long>> {
    return input[1].split(",")
      .mapIndexed { index, s ->
        s to index
      }.filter {
        it.first != "x"
      }.map {
        it.first.toLong() to it.second.toLong()
      }.also { println(it) }
  }

  /**
   * period, diff
   * */
  //couldnt generalize this to 2 diffs...
  fun getDiffTimestamp(one: Long, other: Long, diff: Long): Pair<Long, Long> {
    val lcm = lcm(one, other)
    println("finding $one, $other, diff: $diff, lcm:$lcm")
    val rangeStart = if (other > one) lcm / other else lcm / one
    //for (i in range) {
    for (i in rangeStart downTo 0) {
      println("$other * $i = ${other * i},  ${((other * i) - diff) % one == 0L} (${(other * i - diff)} + ${diff})")
      if (((other * i) - diff) % one == 0L) {
        val res = Pair(lcm, ((other * i) - diff))
        println("first match at ${res.second}, next would be +${res.first} = ${res.second + res.first}")
        return res
      }
    }
    return Pair(-1, -1)
  }

  fun solvePart2(list: List<Pair<Long, Long>>): Long {
    var step = list.first().first
    var timestamp = 0L
    //step forward in time by lcm (all inputs are prime!) until a match is found, then increase the step
    list.drop(1).forEach { (period, offset) ->
      while ((timestamp + offset) % period != 0L) {
        timestamp += step
      }
      println("match for ($period,$offset) @$timestamp, step $step")
      step *= period
    }
    return timestamp
  }

  @Test
  fun validatePart1() {
    assertEquals(295, solvePart1(testInput))
  }

  @Test
  fun part1() {
    assertEquals(119, solvePart1(input))
  }

  @Test
  fun test() {
    val list = parsePart2(
      """
      x
      17,x,13,19
    """.trimIndent().lines()
    )
    var step = list.first().first
    var timestamp = 0L
    list.drop(1).forEach { (period, offset) ->
      while ((timestamp + offset) % period != 0L) {
        timestamp += step
      }
      step *= period
    }
    println(timestamp)
  }

  @Test
  fun validatePart2() {
    assertEquals(1068781, solvePart2(parsePart2(testInput)))
  }

  /**
   * turns out this would help
   * https://en.wikipedia.org/wiki/Chinese_remainder_theorem
   *
   * also covered in
   * https://math.stackexchange.com/questions/2218763/how-to-find-lcm-of-two-numbers-when-one-starts-with-an-offset
   * */
  @Test
  fun part2() {
    assertEquals(1106724616194525, solvePart2(parsePart2(input)))
  }
}
