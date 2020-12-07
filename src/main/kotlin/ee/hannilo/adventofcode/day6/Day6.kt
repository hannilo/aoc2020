package ee.hannilo.adventofcode.day6

class Day6 {

  companion object {

    fun findDistinct(groupAnswers: List<String>): Set<Char> {
      return groupAnswers.reduce { acc, s -> acc + s }.toSet()
    }

    fun findIntersect(groupAnswers: List<String>): Set<Char> {
      return groupAnswers
          .map { it.toSet() }
          .reduce(Set<Char>::intersect)
    }
  }
}
