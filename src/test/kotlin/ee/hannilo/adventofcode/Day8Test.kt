package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day8.Day8
import ee.hannilo.adventofcode.day8.Instruction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day8Test {

  private val input = Util.readInputFile("Day8.txt")

  private val testInput = """
    nop +0
    acc +1
    jmp +4
    acc +3
    jmp -3
    acc -99
    acc +1
    jmp -4
    acc +6
  """.trimIndent().lines()

  //SOLUTIONS
  @Test
  fun validatePart1() {
    val solver = Day8(testInput)
    val acc = solver.run().acc
    Assertions.assertEquals(5, acc)
  }

  @Test
  fun part1() {
    val solver = Day8(input)
    val res = solver.run()
    Assertions.assertFalse(res.finished)
    Assertions.assertEquals(1420, res.acc)
  }

  @Test
  fun validatePart2() {
    val solver = Day8(testInput)
    val alteration = solver.findFrameToAlter()!!
    solver.setRomInstruction(alteration.first, alteration.second)
    val res = solver.run()
    Assertions.assertTrue(res.finished)
    Assertions.assertEquals(8, res.acc)
  }

  @Test
  fun part2() {
    val solver = Day8(input)
    val alteration = solver.findFrameToAlter()!!
    solver.setRomInstruction(alteration.first, alteration.second)
    var res = solver.run()
    Assertions.assertTrue(res.finished)
    Assertions.assertEquals(1245, res.acc)

    solver.setRomInstruction(274, Instruction.NOP)
    res = solver.run()
    Assertions.assertTrue(res.finished)
    Assertions.assertEquals(1245, res.acc)

    val alter2 = solver.findFrameToAlter()
    Assertions.assertNull(alter2)
  }
  //END SOLUTIONS

  @Test
  fun parsesInstructions() {
    val solver = Day8(testInput)
    val stack = solver.getRom()
    Assertions.assertEquals(9, stack.size)
    Assertions.assertEquals(Instruction.NOP, stack.first().instruction)
    Assertions.assertEquals(Instruction.ACC, stack.last().instruction)
    Assertions.assertEquals(6, stack.last().argument)
  }
}
