package ee.hannilo.adventofcode.day19

class Matcher {

  val rules = mutableMapOf<Int, Rule>()

  fun addRule(string: String) {
    string.split(": ").let { list ->
      val id = list.first().toInt()
      if (list[1].trim().matches(Regex("\"[ab]\""))) {
        rules[id] = CharRule(list[1][1])
      } else {
        rules[id] = MetaRule(list[1].trim().split("|").map { l -> l.trim().split(" ").map { it.toInt() } })
      }
    }
  }

  fun matches(string: String, position: Int = 0, ruleList: List<Int> = listOf(0)): Boolean {
    if (ruleList.isEmpty()) {
      return position == string.length
    }
    if (position >= string.length) {
      return ruleList.isEmpty()
    }
    val rule = rules[ruleList.first()]
    return if (rule is CharRule) {
      // simple, just check if idx matches and continue on with the next rule at the next idx
      string[position] == rule.char && matches(string, position + 1, ruleList.drop(1))
    } else {
      // "unwrap" the current rule to its subrules, add those to the front of the list of rules to check from this point onwards
      (rule as MetaRule).rules.any { list: List<Int> -> matches(string, position, list + ruleList.drop(1)) }
    }
  }


}
