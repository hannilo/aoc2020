package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day16.Ticket
import ee.hannilo.adventofcode.day16.TicketFieldRule
import ee.hannilo.adventofcode.day16.TicketScanner
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class Day16Test {

  private val input = Util.readInputLines("Day16.txt").let { Util.chunkListByBlanks(it) }

  private val testInput = """
    class: 1-3 or 5-7
    row: 6-11 or 33-44
    seat: 13-40 or 45-50

    your ticket:
    7,1,14

    nearby tickets:
    7,3,47
    40,4,50
    55,2,20
    38,6,12
  """.trimIndent().lines().let { Util.chunkListByBlanks(it) }

  private val testInputPart2 = """
    class: 0-1 or 4-19
    row: 0-5 or 8-19
    seat: 0-13 or 16-19

    your ticket:
    11,12,13

    nearby tickets:
    3,9,18
    15,1,5
    5,14,9
  """.trimIndent().lines().let { Util.chunkListByBlanks(it) }

  private val testRules = testInput[0].map { TicketFieldRule.parse(it) }
  private val testTicket = testInput[1].last().let { Ticket.parse(it) }
  private val testTickets = testInput[2].drop(1).map { Ticket.parse(it) }

  private val testRules2 = testInputPart2[0].map { TicketFieldRule.parse(it) }
  private val testTicket2 = testInputPart2[1].last().let { Ticket.parse(it) }
  private val testTickets2 = testInputPart2[2].drop(1).map { Ticket.parse(it) }

  private val rules = input[0].map { TicketFieldRule.parse(it) }
  private val ticket = input[1].last().let { Ticket.parse(it) }
  private val tickets = input[2].drop(1).map { Ticket.parse(it) }

  //SOLUTIONS
  @Test
  fun validatePart1() {
    val scanner = TicketScanner(testRules)
    assertEquals(71, testTickets.map { scanner.findNotValidForAnyRule(it) }.flatten().reduce(Int::plus))
  }

  @Test
  fun part1() {
    val scanner = TicketScanner(rules)
    assertEquals(23036, tickets.map { scanner.findNotValidForAnyRule(it) }.flatten().reduce(Int::plus))
  }


  @Test
  fun validatePart2() {
    val scanner = TicketScanner(testRules2)
    val fields = scanner.getFields(testTickets2)
    assertEquals(listOf("row", "class", "seat"), fields)
  }

  @Test
  fun part2() {
    val scanner = TicketScanner(rules)
    val fields = scanner.getFields(tickets)
    assertEquals(rules.size, fields.size)
    val result = fields.mapIndexed { i, s -> i to s }
      .filter { (_, s) -> s.startsWith("departure") }
      .map { (i, _) -> ticket.values[i] }
      .fold(1L) { acc, i -> acc * i.toLong() }
    assertEquals(1909224687553, result)
  }
  //END SOLUTIONS


  @Test
  fun findsInvalids() {
    val scanner = TicketScanner(testRules)
    assertTrue(scanner.findNotValidForAnyRule(testTicket).isEmpty())
    assertTrue(scanner.findNotValidForAnyRule(testTickets[0]).isEmpty())
    assertEquals(listOf(4), scanner.findNotValidForAnyRule(testTickets[1]))
    assertEquals(listOf(55), scanner.findNotValidForAnyRule(testTickets[2]))
    assertEquals(listOf(12), scanner.findNotValidForAnyRule(testTickets[3]))
  }
}
