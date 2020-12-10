package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.Util.Companion.readInputFile
import ee.hannilo.adventofcode.day5.BoardingPass
import ee.hannilo.adventofcode.day5.Day5
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day5Test {

  private val input = readInputFile("Day5.txt").map {
    BoardingPass(it)
  }

  private val testInput = """
    BFFFFFBRRR
    FBFBBFFRLR
    BFFFBBFRRR
    FFFBBBFRRR
    BBFFBBFRLL
  """.trimIndent().lines()

  //SOLUTIONS
  @Test
  fun verifyPart1() {
    val pass = Day5.parse(testInput).maxByOrNull { p -> p.id }!!
    Assertions.assertEquals(820, pass.id)
  }

  @Test
  fun part1() {
    val max = input.maxByOrNull { p -> p.id }!!
    println(max)
    Assertions.assertEquals(861, max.id)
  }

  @Test
  fun solution2() {
    //could use zipping here, and check curr.id + 2 == next.id
    val missing = input.sortedBy { it.id }.let { sorted ->
      sorted.filterIndexed { index, boardingPass ->
        if (index == sorted.size - 1) {
          false
        } else {
          boardingPass.id + 1 != sorted[index + 1].id
        }
      }
    }
    Assertions.assertEquals(1, missing.size)
    println(missing)
    Assertions.assertEquals(633, missing.first().id + 1)
  }
  //END SOLUTIONS

  @Test
  fun parsesBoardingPass() {
    val passes = Day5.parse(testInput)
    Assertions.assertEquals(5, passes.size)
    val iter = passes.iterator()

    var pass = iter.next().also { println(it) }
    Assertions.assertEquals(65, pass.row)

    pass = iter.next().also { println(it) }
    Assertions.assertEquals(44, pass.row)
    Assertions.assertEquals(5, pass.col)
    Assertions.assertEquals(357, pass.id)

    pass = iter.next().also { println(it) }
    Assertions.assertEquals(70, pass.row)
    Assertions.assertEquals(7, pass.col)
    Assertions.assertEquals(567, pass.id)

    pass = iter.next().also { println(it) }
    Assertions.assertEquals(14, pass.row)
    Assertions.assertEquals(7, pass.col)
    Assertions.assertEquals(119, pass.id)

    pass = iter.next().also { println(it) }
    Assertions.assertEquals(102, pass.row)
    Assertions.assertEquals(4, pass.col)
    Assertions.assertEquals(820, pass.id)
  }
}
