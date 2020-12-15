package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day15.Game
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15Test {

  //SOLUTIONS
  @Test
  fun validatePart1() {
    var solver = Game("1,3,2")
    repeat(2017) {
      solver.next()
    }
    assertEquals(1, solver.previous)

    solver = Game("3,1,2")
    repeat(2017) {
      solver.next()
    }
    assertEquals(1836, solver.previous)
  }

  @Test
  fun part1() {
    val solver = Game("20,9,11,0,1,2")
    for (i in solver.completedTurn until 2020) {
      solver.next()
    }
    assertEquals(1111, solver.previous)
  }

  @Test
  fun part2() {
    val solver = Game("20,9,11,0,1,2")
    val timer = StopWatch.start()
    for (i in solver.completedTurn until 30000000) {
      solver.next()
    }
    println("took ${timer.lap()}ms")
    assertEquals(48568, solver.previous)
  }
  //END SOLUTIONS


  @Test
  fun initializes() {
    val solver = Game("0,3,6")
    assertEquals(1, solver.memory[0]!!)
    assertEquals(2, solver.memory[3]!!)
    assertEquals(3, solver.memory[6]!!)
    assertEquals(3, solver.completedTurn)
    assertEquals(6, solver.previous)
  }

  @Test
  fun setsNext() {
    val solver = Game("0,3,6")
    solver.next()
    assertEquals(1, solver.memory[0]!!)
    assertEquals(4, solver.completedTurn)
    assertEquals(0, solver.previous)

    for (i in 4 until 2020) {
      solver.next()
    }
    assertEquals(436, solver.previous)
  }
}
