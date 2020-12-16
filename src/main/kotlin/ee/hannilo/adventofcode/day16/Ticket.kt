package ee.hannilo.adventofcode.day16

data class Ticket(val values: List<Int>) {
  companion object {
    fun parse(string: String): Ticket {
      return Ticket(string.split(",").map { it.toInt() })
    }
  }
}
