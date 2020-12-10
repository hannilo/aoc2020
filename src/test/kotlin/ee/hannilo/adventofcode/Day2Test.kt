package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day2.Day2
import ee.hannilo.adventofcode.day2.PasswordEntry
import ee.hannilo.adventofcode.day2.policy.SingleCharPolicyMetadata
import ee.hannilo.adventofcode.day2.policy.SledPolicy
import ee.hannilo.adventofcode.day2.policy.TobogganPolicy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day2Test {

  private val input = Util.readInputFile("Day2.txt").map { Day2.parseEntry(it) }

  private val testInput = listOf(
    "1-3 a: abcde",
    "1-3 b: cdefg",
    "2-9 c: ccccccccc",
  ).map { Day2.parseEntry(it) }

  //SOLUTIONS
  @Test
  fun verifyPart1() {
    val valids = Day2.getValids(testInput, SledPolicy())
    Assertions.assertEquals(2, valids.size)
  }

  @Test
  fun part1() {
    val valids = Day2.getValids(input, SledPolicy())
    Assertions.assertEquals(396, valids.size)
  }

  @Test
  fun verifySolution2() {
    val valids = Day2.getValids(testInput, TobogganPolicy())
    Assertions.assertEquals(1, valids.size)
  }

  @Test
  fun solution2() {
    val valids = Day2.getValids(input, TobogganPolicy())
    Assertions.assertEquals(428, valids.size)
  }
  //END SOLUTIONS

  @Test
  fun isValidOld() {
    val list = listOf(
      PasswordEntry("a", SingleCharPolicyMetadata('a', 1, 2)),
      PasswordEntry("aa", SingleCharPolicyMetadata('a', 1, 2)),
      PasswordEntry("aaa", SingleCharPolicyMetadata('a', 1, 2)),
      PasswordEntry("b", SingleCharPolicyMetadata('a', 1, 2)),
      PasswordEntry("baca", SingleCharPolicyMetadata('a', 1, 2)),
      PasswordEntry("bacaa", SingleCharPolicyMetadata('a', 1, 2)),
    )
    val result = Day2.getValids(list, SledPolicy())
    Assertions.assertEquals(3, result.size)
  }

  @Test
  fun isValidNew() {
    val list = listOf(
      PasswordEntry("aa", SingleCharPolicyMetadata('a', 1, 2)),
      PasswordEntry("ab", SingleCharPolicyMetadata('a', 1, 2)),
      PasswordEntry("ba", SingleCharPolicyMetadata('a', 1, 2)),
      PasswordEntry("bb", SingleCharPolicyMetadata('a', 1, 2)),
      PasswordEntry("aba", SingleCharPolicyMetadata('a', 1, 3)),
      PasswordEntry("abc", SingleCharPolicyMetadata('a', 1, 3)),
      PasswordEntry("cba", SingleCharPolicyMetadata('a', 1, 3)),
    )
    val result = Day2.getValids(list, TobogganPolicy())
    Assertions.assertEquals(4, result.size)
  }
}
