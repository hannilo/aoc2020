package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day22.Combat
import ee.hannilo.adventofcode.day22.CombatHand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day22Test {

  private val testInput = """
    Player 1:
    9
    2
    6
    3
    1

    Player 2:
    5
    8
    4
    7
    10
  """.trimIndent().lines().let { Util.chunkListByBlanks(it) }
    .map { l -> CombatHand(l.first().substringBefore(":"), l.drop(1).map { it.toInt() }) }

  private val input = Util.readInputLines("Day22.txt").let { Util.chunkListByBlanks(it) }.map { l ->
    CombatHand(l.first().substringBefore(":"), l.drop(1).map { it.toInt() })
  }

  @Test
  fun validatePart1() {
    val combat = Combat()
    val winner = combat.play(testInput.first(), testInput.last())
    assertEquals(306, combat.calculateScore(winner))
  }

  @Test
  fun part1() {
    val combat = Combat()
    val winner = combat.play(input.first(), input.last())
    assertEquals(32083, combat.calculateScore(winner))
    val temp = setOf("wasd").toHashSet()
    println(temp)
  }

  @Test
  fun validatePart2() {
    val combat = Combat()
    val winner = combat.playRecursive(testInput.first(), testInput.last())
    assertEquals(291, combat.calculateScore(winner))
  }

  @Test
  fun part2() {
    val combat = Combat(printer = {})
    val winner = combat.playRecursive(input.first(), input.last())
    assertEquals(35495, combat.calculateScore(winner))
  }

  @Test
  fun recursionHalts() {
    val haltInput = """
      Player 1:
      43
      19

      Player 2:
      2
      29
      14
    """.trimIndent().lines().let { Util.chunkListByBlanks(it) }.map { l ->
      CombatHand(l.first().substringBefore(":"), l.drop(1).map { it.toInt() })
    }

    val combat = Combat()
    val winner = combat.playRecursive(haltInput.first(), haltInput.last())
    assertEquals(105, combat.calculateScore(winner))
  }


}
