package ee.hannilo.adventofcode.day19

interface Rule

class CharRule(val char: Char) : Rule {
  override fun toString(): String {
    return "Rule($char)"
  }
}

class MetaRule(val rules: List<List<Int>>) : Rule {
  override fun toString(): String {
    return "Rule($rules)"
  }
}
