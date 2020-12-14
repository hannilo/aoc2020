package ee.hannilo.adventofcode


import ee.hannilo.adventofcode.day11.Day11
import ee.hannilo.adventofcode.day11.Day11.Companion.OCCUPIED
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class Day11Test {

  private val testInput = """
    L.LL.LL.LL
    LLLLLLL.LL
    L.L.L..L..
    LLLL.LL.LL
    L.LL.LL.LL
    L.LLLLL.LL
    ..L.L.....
    LLLLLLLLLL
    L.LLLLLL.L
    L.LLLLL.LL
  """.trimIndent()

  val input = Util.readInputFile("Day11.txt")

  //SOLUTIONS
  @Test
  fun validatePart1() {
    val solver = Day11(testInput)
    while (solver.alteredPrevious) {
      solver.nextByNeighbors()
    }
    assertEquals(6, solver.iteration)
    val expected = """
      #.#L.L#.##
      #LLL#LL.L#
      L.#.L..#..
      #L##.##.L#
      #.#L.LL.LL
      #.#L#L#.##
      ..L.L.....
      #L#L##L#L#
      #.LLLLLL.L
      #.#L#L#.##
    """.trimIndent()

    assertEquals(expected, solver.getString())

    assertEquals(37, solver.getString().count { it == OCCUPIED })
  }

  @Test
  fun part1() {
    val solver = Day11(input)
    while (solver.alteredPrevious) {
      solver.nextByNeighbors()
    }
    assertEquals(71, solver.iteration)
    assertEquals(2406, solver.getString().count { it == OCCUPIED })
  }

  @Test
  fun validatePart2() {
    val solver = Day11(testInput)
    while (solver.alteredPrevious) {
      solver.nextByVisible()
    }
    assertEquals(7, solver.iteration)
    val expected = """
      #.L#.L#.L#
      #LLLLLL.LL
      L.L.L..#..
      ##L#.#L.L#
      L.L#.LL.L#
      #.LLLL#.LL
      ..#.L.....
      LLL###LLL#
      #.LLLLL#.L
      #.L#LL#.L#
    """.trimIndent()

    assertEquals(expected, solver.getString())

    assertEquals(26, solver.getString().count { it == OCCUPIED })
  }

  @Test
  fun part2() {
    val solver = Day11(input)
    while (solver.alteredPrevious) {
      solver.nextByVisible()
    }
    assertEquals(90, solver.iteration)
    assertEquals(2149, solver.getString().count { it == OCCUPIED })
  }
  //END SOLUTIONS

  @Test
  fun calculatestNextByVisible() {
    val solver = Day11(testInput)

    solver.nextByVisible()
    var expected = """
      #.##.##.##
      #######.##
      #.#.#..#..
      ####.##.##
      #.##.##.##
      #.#####.##
      ..#.#.....
      ##########
      #.######.#
      #.#####.##
    """.trimIndent()
    assertEquals(1, solver.iteration)
    assertEquals(expected, solver.getString())

    assertEquals(4, solver.countVisible(8, 0))

    solver.nextByVisible()
    expected = """
      #.LL.LL.L#
      #LLLLLL.LL
      L.L.L..L..
      LLLL.LL.LL
      L.LL.LL.LL
      L.LLLLL.LL
      ..L.L.....
      LLLLLLLLL#
      #.LLLLLL.L
      #.LLLLL.L#
    """.trimIndent()
    assertEquals(2, solver.iteration)
    assertEquals(expected, solver.getString())

  }

  @Test
  fun countsVisible_ex1() {
    val solver = Day11("""
      .......#.
      ...#.....
      .#.......
      .........
      ..#L....#
      ....#....
      .........
      #........
      ...#.....
    """.trimIndent())

    val vis = solver.countVisible(4, 3)
    assertEquals(8, vis)
  }

  @Test
  fun countsVisible_ex2() {
    val solver = Day11("""
      .............
      .L.L.#.#.#.#.
      .............
    """.trimIndent())

    val vis = solver.countVisible(1, 1)
    assertEquals(0, vis)
  }

  @Test
  fun countsVisible_ex3() {
    val solver = Day11("""
      .##.##.
      #.#.#.#
      ##...##
      ...L...
      ##...##
      #.#.#.#
      .##.##.
    """.trimIndent())

    val vis = solver.countVisible(3, 3)
    assertEquals(0, vis)
  }


  @Test
  fun loadsBoard() {
    val solver = Day11(testInput)
    assertEquals(10, solver.height)
    assertEquals(10, solver.width)

    println(solver.board)
    println(solver.state)

    assertEquals(testInput, solver.getString())
  }

  @Test
  fun countsNeighbors() {
    val solver = Day11(
      """
      #.##.##.##
      #######.##
      #.#.#..#..
      ####.##.##
      #.##.##.##
      #.#####.##
      ..#.#.....
      ##########
      #.######.#
      #.#####.##
    """.trimIndent()
    )
    var n = 0
    n = solver.countNeighbors(4, 0)
    assertEquals(3, n)
  }

  @Test
  fun calculatestNextState() {
    val solver = Day11(testInput)

    val n = solver.countNeighbors(4, 3)

    solver.nextByNeighbors()
    println(solver.getString())
    var expected = """
      #.##.##.##
      #######.##
      #.#.#..#..
      ####.##.##
      #.##.##.##
      #.#####.##
      ..#.#.....
      ##########
      #.######.#
      #.#####.##
    """.trimIndent()
    assertEquals(expected, solver.getString())

    solver.nextByNeighbors()
    expected = """
      #.LL.L#.##
      #LLLLLL.L#
      L.L.L..L..
      #LLL.LL.L#
      #.LL.LL.LL
      #.LLLL#.##
      ..L.L.....
      #LLLLLLLL#
      #.LLLLLL.L
      #.#LLLL.##
    """.trimIndent()
    assertEquals(expected, solver.getString())
  }

  @Test
  fun detectsNoChange() {

    val str = """
      #.#L.L#.##
      #LLL#LL.L#
      L.#.L..#..
      #L##.##.L#
      #.#L.LL.LL
      #.#L#L#.##
      ..L.L.....
      #L#L##L#L#
      #.LLLLLL.L
      #.#L#L#.##
    """.trimIndent()
    val solver = Day11(str)
    solver.nextByNeighbors()
    assertEquals(str, solver.getString())
    assertFalse(solver.alteredPrevious)
  }
}
