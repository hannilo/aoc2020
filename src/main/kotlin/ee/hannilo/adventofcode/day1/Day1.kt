package ee.hannilo.adventofcode.day1

import java.util.*

/**
 * Finds the first 2 numbers in the list with the given sum.
 *
 * Does not recurse.
 * */
fun List<Int>.findSum(sum: Int): Pair<Int, Int>? {
  this.takeIf { it.size >= 2 }?.forEachIndexed { iIdx, i ->
    this.forEachIndexed { jIdx, j ->
      if (iIdx != jIdx && (sum - i) == j) return Pair(i, j)
    }
  }
  return null
}

class Day1 {

  companion object {

    fun findSum(input: List<Int>, sumToSearch: Int): Pair<Int, Int>? {
      input.takeIf { input.size >= 2 }?.forEachIndexed { iIdx, i ->
        input.forEachIndexed { jIdx, j ->
          if (iIdx != jIdx && (sumToSearch - i) == j) return Pair(i, j)
        }
      }
      return null
    }


    fun findSumN(input: List<Int>, sumToSearch: Int, n: Int, startIdx: Int = 0): List<Int> {
      assert(input.size >= n) { "Should have at least $n elements, gave ${input.size} : $input, searching for $sumToSearch" }

      //known input
      if (sumToSearch < 0) {
        return emptyList()
      }

      if (n == 2) {
        return findSum(input.subList(startIdx, input.size), sumToSearch)?.toList()?.reversed() ?: emptyList()
      }

      for (i in startIdx until input.size) {
        val target = sumToSearch - input[i]
        val result = findSumN(input, target, n - 1, i)
        if (result.isNotEmpty()) {
          return listOf(input[i]) + result
        }
      }

      return emptyList()
    }
  }
}
