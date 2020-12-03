package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day1.Day1
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day1Test {

  private val input = Util.readInputFile("Day1.txt")
    .map { s: String ->
      Integer.parseInt(s)
    }

  private val testinput = listOf(
    1721,
    979,
    366,
    299,
    675,
    1456,
  )

  //SOLUTIONS
  @Test
  fun verifySolution1() {
    val solution = Day1.findSum(testinput, 2020)?.let { it.first * it.second }
    Assertions.assertEquals(514579, solution)
  }

  @Test
  fun solution1() {
    println(input)
    val pair = Day1.findSum(input, 2020)!!
    val solution = pair.first * pair.second
    println("Day1 solution: $solution, found pair $pair")
  }

  @Test
  fun verifySolution2() {
    val solution = Day1.findSumN(testinput, 2020, 3).reduce { acc, i -> acc * i }
    Assertions.assertEquals(241861950, solution)
  }

  @Test
  fun solution2() {
    val result = Day1.findSumN(input, 2020, 3)
    val solution = result.reduce { acc, i -> acc * i }
    println("Day1 part 2 solution: $solution, found triplet $result")
  }
  //END SOLUTIONS


  @Test
  fun findSimple() {
    val list = listOf(1, 2, 1000, 3, 1020)
    val pair = Day1.findSum(list, 2020)!!
    Assertions.assertEquals(Pair(1000, 1020), pair)
  }

  @Test
  fun findNone() {
    val list = listOf(1, 4, 3)
    val pair = Day1.findSum(list, 3)
    Assertions.assertEquals(null, pair)
  }

  @Test
  fun findTriple() {
    val list = listOf(1, 20, 3, 1100, 900, 4)
    val result = Day1.findSumN(list, 2020, 3)
    Assertions.assertEquals(listOf(20, 1100, 900), result)
  }

  @Test
  fun findTripleLast() {
    val list = listOf(50, 2, 2, 900, 50)
    val result = Day1.findSumN(list, 1000, 3)
    Assertions.assertEquals(listOf(50, 900, 50), result)
  }

  @Test
  fun findNoneTriple() {
    val list = listOf(1, 20, 3, 1100, 900, 4)
    val result = Day1.findSumN(list, 5000, 3)
    Assertions.assertEquals(emptyList<Int>(), result)
  }

  @Test
  fun findAny() {
    val list = listOf(1, -2, -3, 4, -6, 7, -10, 20, 21, 12, -3)
    val result = Day1.findSumN(list, 24, 3)
    println(result)
  }
}
