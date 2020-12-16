package ee.hannilo.adventofcode.day16

import java.lang.IllegalArgumentException

data class TicketFieldRule(
  val name: String,
  val ranges: List<IntRange>
) {

  fun validate(int: Int): Boolean {
    ranges.forEach {
      if (int in it) return true
    }
    return false
  }

  companion object {
    private val FIELD_RULE_REGEX = Regex("([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)")
    fun parse(string: String): TicketFieldRule {
      FIELD_RULE_REGEX.matchEntire(string)?.destructured?.let { (name, s1, e1, s2, e2) ->
        return TicketFieldRule(name, listOf(s1.toInt()..e1.toInt(), s2.toInt()..e2.toInt()))
      } ?: throw IllegalArgumentException("Invalid rule string: $string")
    }
  }
}
