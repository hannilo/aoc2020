package ee.hannilo.adventofcode.day9

import java.util.*

/**
 * Build n trees, with leaves storing sums.
 *
 * Trees because I anticipated depth shenanigans.
 * */
class Day9(private val intList: List<Long>, private val depth: Int = 1, private val memSize: Int = 5) {

  val trees: LinkedList<Node> = LinkedList(intList.take(memSize).mapIndexed { iIdx, i ->
    Node(
      i, null, LinkedList(intList.take(memSize).filterIndexed { jIdx, _ -> jIdx != iIdx }.map { j -> Node(j, i + j) })
    )
  })

  init {
    require(memSize <= intList.size) { "Memory size ($memSize) should be <= list size (${intList.size})" }
  }

  /**
   * Returns the final passing index, intList.size if all pass
   * */
  fun load(): Int {
    intList.takeLast(intList.size - memSize).forEachIndexed { index, i ->
      next(i).let {
        if (!it) {
          return index + memSize
        }
      }
    }
    return intList.size
  }

  fun getLeaves(): List<Pair<Long, List<Long>>> {
    return trees.map { t -> t.value to t.children.map { it.sumWithParent!! } }
  }

  /**
   * Adds element to trees if it passes validation
   * @return true addition was successful
   * */
  fun next(i: Long): Boolean {
    if (!validate(i)) {
      return false
    }
    trees.remove()
    trees.forEach { t -> t.children.let { it.remove(); it.add(Node(i, t.value + i)) } }
    trees.add(Node(i, null, LinkedList(trees.take(trees.size).map { Node(it.value, it.value + i) })))
    return true
  }

  fun validate(next: Long): Boolean {
    return getLeaves().map { it.second }.flatten().contains(next)
  }

  companion object {

    fun findContiguousSum(sumToFind: Long, list: List<Long>): List<Long> {
      var start = 0
      var end = 1
      while (end < list.size) {
        val window = list.subList(start, end)
        window.sum().compareTo(sumToFind).let {
          when {
            it == 0 -> return window
            it > 0 -> start++
            else -> end++
          }
        }
      }
      return emptyList()
    }
  }
}
