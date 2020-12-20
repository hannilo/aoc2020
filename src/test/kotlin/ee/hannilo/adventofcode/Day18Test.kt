package ee.hannilo.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

//really quick and REALLY ugly
//should split into op and value queues using shunting-yard, should have seen that one coming
class Day18Test {

  fun calculate(string: String): Long {
    return calculate(string.split(" ").flatMap { it.toList().map { it.toString() } })
  }

  fun calculate2(string: String): Long {
    return calculate2(string.split(" ").flatMap { it.toList().map { it.toString() } })
  }

  fun calculate(list: List<String>): Long {
    println("calc: $list")
    val queue = LinkedList(list)
    var acc = next(queue)

    while (queue.isNotEmpty()) {
      val op = queue.poll()
      if (op == "+") {
        acc += next(queue)
      } else if (op == "*") {
        acc *= next(queue)
      }
    }
    return acc
  }

  fun calculate2(list: List<String>): Long {
    println("calc: $list")
    val queue = LinkedList(list)
    var acc = next(queue, true)

    while (queue.isNotEmpty()) {
      val op = queue.poll()
      println("acc : $acc, op $op")
      if (op == "+") {
        val next = next(queue, true)
        println("$acc += $next")
        acc += next
      } else if (op == "*") {
        val next = next(queue, true)
        val nextOp = queue.poll()
        if (nextOp == "*" || nextOp == null) {
          println("$acc *= $next")
          nextOp?.let { queue.push(it) } //only works if there are 2 ops
          acc *= next
        } else {
          queue.addLast("*")
          queue.addLast("$acc")
          val newNext = next(queue, true)
          println("acc = $next + $newNext")
          acc = next + newNext
        }
      }
    }
    println("acc:$acc")
    return acc
  }


  fun next(queue: LinkedList<String>, calc2: Boolean = false): Long {
    val first = queue.poll()
    return if (first == "(") {
      var brackets = 1
      val sublist = mutableListOf(first)
      while (brackets > 0) {
        val next = queue.poll()
        if (next == "(") {
          brackets++
        } else if (next == ")") {
          brackets--
        }
        sublist.add(next)
      }
      val res = sublist.drop(1).dropLast(1).let {
        if (calc2) calculate2(it) else calculate(it)
      }
      return res
    } else {
      val res = first.toString().toLong()
      res
    }
  }

  @Test
  fun validatePart1() {
    assertEquals(437, calculate("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
    assertEquals(12240, calculate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
    assertEquals(13632, calculate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))
  }

  @Test
  fun part1() {
    val input = Util.readInputLines("Day18.txt")
    val res = input.fold(0L) { acc, s -> acc + calculate(s) }
    assertEquals(4940631886147, res)
  }

  @Test
  fun validatePart2() {
    assertEquals(231, calculate2("1 + 2 * 3 + 4 * 5 + 6"))
    assertEquals(51, calculate2("1 + (2 * 3) + (4 * (5 + 6))"))
    assertEquals(46, calculate2("2 * 3 + (4 * 5)"))

    assertEquals(1440, calculate2("8 * 3 + 9 + 3 * 4 * 3"))
    assertEquals(1445, calculate2("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
    assertEquals(669060, calculate2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
    assertEquals(23340, calculate2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))
  }

  @Test
  fun part2() {
    val input = Util.readInputLines("Day18.txt")
    val res = input.fold(0L) { acc, s -> acc + calculate2(s) }
    assertEquals(283582817678281, res)
  }

  @Test
  fun evaluatesSingle() {
    val res = calculate("1 + 2 * 3 + 4 * 5 + 6")
    assertEquals(71, res)
  }

  @Test
  fun evaluatesParentheses() {
    val res = calculate("2 * 3 + (4 * 5)")
    assertEquals(26, res)
  }


}
