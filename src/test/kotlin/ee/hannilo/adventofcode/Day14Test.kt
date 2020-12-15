package ee.hannilo.adventofcode

import org.junit.jupiter.api.Test

import ee.hannilo.adventofcode.day14.Emulator
import ee.hannilo.adventofcode.day14.EmulatorV2
import org.junit.jupiter.api.Assertions.assertEquals

class Day14Test {

  private val input = Util.readInputLines("Day14.txt")

  private val testInput = """
    mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
    mem[8] = 11
    mem[7] = 101
    mem[8] = 0
  """.trimIndent().lines()

  @Test
  fun validatePart1() {
    val solver = Emulator(testInput.first().substringAfter("= "))
    solver.exec(testInput[1])
    assertEquals(73, solver.memory[8])
    solver.exec(testInput[2])
    assertEquals(101, solver.memory[7])
    solver.exec(testInput[3])
    assertEquals(64, solver.memory[8])
    assertEquals(165, solver.memory.map { it.value }.sum())
  }

  @Test
  fun part1() {
    val solver = Emulator(input.first().substringAfter("= "))
    input.drop(1).forEach {
      solver.exec(it)
    }
    assertEquals(14722016054794, solver.memory.map { it.value }.sum())
  }

  @Test
  fun validatePart2() {
    val testInput2 = """
      mask = 000000000000000000000000000000X1001X
      mem[42] = 100
      mask = 00000000000000000000000000000000X0XX
      mem[26] = 1
    """.trimIndent().lines()
    val solver = EmulatorV2(testInput2.first().substringAfter("= "))
    println(solver.mask)
    testInput2.drop(1).forEach {
      solver.exec(it)
    }
    assertEquals(208, solver.memory.map { it.value }.sum())
  }

  @Test
  fun part2() {
    val solver = EmulatorV2(input.first().substringAfter("= "))
    input.drop(1).forEach {
      solver.exec(it)
    }
    assertEquals(3618217244644, solver.memory.map { it.value }.sum())
  }

}
