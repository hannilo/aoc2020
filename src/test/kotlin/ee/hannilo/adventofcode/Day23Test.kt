package ee.hannilo.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day23Test {

  val testInput = "389125467".map { it.toString().toInt() }.toIntArray()

  private val input = "952316487".map { it.toString().toInt() }.toIntArray()

  class Cup(
    val label: Int,
    var parent: Cup?,
    var child: Cup?
  ) {

    fun getNext(n: Int): List<Cup> {
      val res = mutableListOf<Cup>()
      for (i in 1..n) {
        val child = res.lastOrNull()?.child ?: this.child!!
        res.add(child)
      }
      return res
    }

    override fun toString(): String {
      return "Cup(${parent?.label}<$label>${child?.label})"
    }
  }

  fun link(parent: Cup, child: Cup) {
    parent.child = child
    child.parent = parent
  }

  fun parse(arr: IntArray): Array<Cup?> {
    val cupArr = arrayOfNulls<Cup>(arr.maxOrNull()!! + 1)
    var prev: Cup? = null
    var first: Cup? = null
    var last: Cup? = null
    for (i in arr) {
      val cup = Cup(i, prev, null)
      if (first == null) first = cup
      if (prev != null) prev.child = cup
      prev = cup
      last = cup
      cupArr[i] = cup //cheap lookup by label
    }
    link(last!!, first!!)
    for (c in cupArr.indices) {
      if (c != 0) {
        assert(c == cupArr[c]!!.label)
        assert(cupArr[c] == cupArr[c]!!.child!!.parent)
        assert(cupArr[c] == cupArr[c]!!.parent!!.child)
      }
    }
    return cupArr
  }

  fun parseM(arr: IntArray, count: Int = 1_000_000): Array<Cup?> {
    val complete = arr.toMutableList().apply { addAll(arr.size + 1..count) }.toIntArray()
    println(complete.size)
    return parse(complete)
  }

  //could use an extension fn
  fun solve(cups: Array<Cup?>, moves: Int, startingFrom: Int): String {
    val min = 1
    val max = cups.size - 1

    var turns = 0
    var current = cups[startingFrom]!!
    repeat(moves) { turn ->
      turns = turn
      val picked = current.getNext(3)
      //The crab selects a destination cup: the cup with a label equal to the current cup's label minus one.
      var destinationLabel = current.label - 1
      //If this would select one of the cups that was just picked up,
      // the crab will keep subtracting one until it finds a cup that wasn't just picked up.
      while (picked.any { it.label == destinationLabel } || destinationLabel < min) {
        destinationLabel--
        //If at any point in this process the value goes below the lowest value on any cup's label
        if (destinationLabel < min) destinationLabel = max
      }

      val destination = cups[destinationLabel]!!
      val destChild = destination.child!!
      val pickChild = picked.last().child!!

      // ABCDE -> ADCBE
      // B-D
      link(destination, picked.first())
      // C-E
      link(picked.last(), destChild)
      // A-D
      link(current, pickChild)

      current = current.child!!
    }

    println("done : ${turns + 1}")
    for (c in cups.indices) {
      if (c != 0) {
        assert(c == cups[c]!!.label)
        assert(cups[c] == cups[c]!!.child!!.parent)
        assert(cups[c] == cups[c]!!.parent!!.child)
      }
    }

    var cup = cups[1]!!
    if (cups.size > 11) { // yeah
      return "${(cup.child!!.label.toLong() * cup.child!!.child!!.label.toLong())}"
    }
    var str = ""
    repeat(cups.size - 2) {
      str += cup.child!!.label
      cup = cup.child!!
    }
    return str
  }


  @Test
  fun validatePart1() {
    val cups = parse(testInput)
    println(cups)
    assertEquals("92658374", solve(cups, 10, testInput.first()))
    val cups2 = parse(testInput)
    assertEquals("67384529", solve(cups2, 100, testInput.first()))
  }

  @Test
  fun part1() {
    val cups = parse(input)
    assertEquals("25398647", solve(cups, 100, input.first()))
  }

  @Test
  fun validatePart2() {
    val timer = StopWatch.start()
    val cups = parseM(testInput)
    val res = solve(cups, 10000000, testInput.first())
    val time = timer.lap()
    println("done: $time ms")
    assertEquals("149245887792", res)
  }

  @Test
  fun part2() {
    val timer = StopWatch.start()
    val cups = parseM(input)
    val res = solve(cups, 10000000, input.first())
    val time = timer.lap()
    println("done: $time ms")
    assertEquals("363807398885", res)
  }

  @Test
  fun parses() {
    val cups = parse(testInput)
    assertEquals(10, cups.size)
  }
}
