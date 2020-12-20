package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day19.Matcher
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day19Test {

  private val testInput = """
    0: 4 1 5
    1: 2 3 | 3 2
    2: 4 4 | 5 5
    3: 4 5 | 5 4
    4: "a"
    5: "b"

    ababbb
    bababa
    abbbab
    aaabbb
    aaaabbb
  """.trimIndent().lines().let { Util.chunkListByBlanks(it) }

  private val testInput2 = """
    42: 9 14 | 10 1
    9: 14 27 | 1 26
    10: 23 14 | 28 1
    1: "a"
    11: 42 31
    5: 1 14 | 15 1
    19: 14 1 | 14 14
    12: 24 14 | 19 1
    16: 15 1 | 14 14
    31: 14 17 | 1 13
    6: 14 14 | 1 14
    2: 1 24 | 14 4
    0: 8 11
    13: 14 3 | 1 12
    15: 1 | 14
    17: 14 2 | 1 7
    23: 25 1 | 22 14
    28: 16 1
    4: 1 1
    20: 14 14 | 1 15
    3: 5 14 | 16 1
    27: 1 6 | 14 18
    14: "b"
    21: 14 1 | 1 14
    25: 1 1 | 1 14
    22: 14 14
    8: 42
    26: 14 22 | 1 20
    18: 15 15
    7: 14 5 | 1 21
    24: 14 1

    abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
    bbabbbbaabaabba
    babbbbaabbbbbabbbbbbaabaaabaaa
    aaabbbbbbaaaabaababaabababbabaaabbababababaaa
    bbbbbbbaaaabbbbaaabbabaaa
    bbbababbbbaaaaaaaabbababaaababaabab
    ababaaaaaabaaab
    ababaaaaabbbaba
    baabbaaaabbaaaababbaababb
    abbbbabbbbaaaababbbbbbaaaababb
    aaaaabbaabaaaaababaa
    aaaabbaaaabbaaa
    aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
    babaaabbbaaabaababbaabababaaab
    aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
  """.trimIndent().lines().let { Util.chunkListByBlanks(it) }

  private val input = Util.readInputLines("Day19.txt").let { Util.chunkListByBlanks(it) }

  @Test
  fun validatePart1() {
    val matcher = Matcher()
    testInput.first().forEach { matcher.addRule(it) }
    println(matcher.rules)
    assertTrue(matcher.matches(string = "a", ruleList = listOf(4)))
    assertFalse(matcher.matches(string = "b", ruleList = listOf(4)))
    assertTrue(matcher.matches(string = "ababbb", ruleList = listOf(0)))
    assertTrue(matcher.matches(string = "abbbab", ruleList = listOf(0)))
    assertFalse(matcher.matches(string = "aaaabbb", ruleList = listOf(0)))
  }

  @Test
  fun part1() {
    val matcher = Matcher()
    input.first().forEach { matcher.addRule(it) }
    val count = input[1].count { matcher.matches(it, ruleList = listOf(0)) }
    assertEquals(210, count)
  }

  @Test
  fun validatePart2() {
    val alterations = """
      8: 42 | 42 8
      11: 42 31 | 42 11 31
    """.trimIndent().lines()

    val matcher = Matcher()
    testInput2.first().forEach { matcher.addRule(it) }
    assertEquals(3, testInput2[1].count { matcher.matches(it) })

    alterations.forEach { matcher.addRule(it) }
    val altered = testInput2[1].map { it to matcher.matches(it) }
    altered.forEach { println(it) }
    assertEquals(12, altered.count { it.second })
  }

  @Test
  fun part2() {
    val matcher = Matcher()
    val alterations = """
      8: 42 | 42 8
      11: 42 31 | 42 11 31
    """.trimIndent().lines()
    input.first().forEach { matcher.addRule(it) }
    alterations.forEach { matcher.addRule(it) }
    val count = input[1].count { matcher.matches(it) }
    assertEquals(422, count)
  }

  @Test
  fun testPart2() {
    val alterations = """
      8: 42 | 42 8
      11: 42 31 | 42 11 31
    """.trimIndent().lines()

    val matcher = Matcher()
    testInput2.first().forEach { matcher.addRule(it) }
    alterations.forEach { matcher.addRule(it) }
    assertEquals(31, matcher.rules.size)

    assertFalse(matcher.matches("aaaabbaaaabbaaa"))
  }
}
