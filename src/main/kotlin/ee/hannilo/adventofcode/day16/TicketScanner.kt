package ee.hannilo.adventofcode.day16

class TicketScanner(
  private val rules: List<TicketFieldRule>
) {

  /**
   * Find values that do not match any rule
   * */
  fun findNotValidForAnyRule(ticket: Ticket): List<Int> {
    return ticket.values.filter { v ->
      !rules.map { r ->
        r.validate(v)
      }.reduce { acc, b -> acc || b }
    }
  }


  /**
   * Filters all invalid tickets and finds a list of fields that matches everything
   * */
  fun getFields(tickets: List<Ticket>): List<String> {
    var rulelist = tickets.filter { findNotValidForAnyRule(it).isEmpty() }
      .map { t ->
        t.values.map { v ->
          rules.filter { r -> r.validate(v) }.map { it.name }.toSet()
        }
      }.reduce { acc, list ->
        list.mapIndexed { i, set -> acc[i].intersect(set) }
      }
    while (rulelist.any { it.size > 1 }) {
      val unavailable = rulelist.filter { it.size == 1 }.reduce { acc, set -> acc.plus(set) }
      rulelist = rulelist.map {
        if (it.size > 1) it.minus(unavailable) else it
      }
    }
    return rulelist.map { it.first() }
  }
}
