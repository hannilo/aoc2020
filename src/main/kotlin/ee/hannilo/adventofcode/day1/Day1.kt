package ee.hannilo.adventofcode.day1

import mu.KotlinLogging
import java.util.*
import kotlin.math.log

class Day1 {

  companion object {
    private val logger = KotlinLogging.logger {}

    fun findSum(input: List<Int>, sumToSearch: Int): Pair<Int, Int>? {
      assert(input.size > 1) { "Should have at least 2 elements" }
      logger.info { "Finding $sumToSearch from 2 of ${input.size} elements: $input" }
      val queue = LinkedList(input)
      while (queue.peek() != null) {
        val el = queue.poll()
        val target = sumToSearch - el
        queue.forEach {
          if (it == target) {
            logger.info { "$el + $it: ${el + it}" }
            return Pair(el, it)
          }
        }
      }
      logger.warn { "No pair found within $input" }
      return null
    }

    /**
     * Finds n numbers in input that add up to sumToSearch. Does not support negative sums.
     * */
    fun findSumN(input: List<Int>, sumToSearch: Int, n: Int): List<Int> {
      assert(input.size >= n) { "Should have at least $n elements, gave ${input.size} : $input, searching for $sumToSearch" }
      if (sumToSearch < 0) {
        return emptyList()
      }
      //this is not efficient, recreates the queue every iteration, should be replaced by array idx access
      val queue = LinkedList(input)
      while (queue.size >= n) {
        logger.trace { "Finding $sumToSearch from $n of ${queue.size} elements: $queue" }

        val el = queue.poll()
        val target = sumToSearch - el
        if (target < 0) continue

        var result = emptyList<Int>()
        if (n == 2) {
          queue.forEach {
            if (it == target) {
              logger.info { "$el + $it: ${el + it}" }
              result = listOf(it)
            }
          }
        } else {
          result = findSumN(queue, target, n - 1)
        }
        if (result.isNotEmpty()) {
          return result.toMutableList().apply { this.add(0, el) }
        }
      }
      logger.trace { "No result for $sumToSearch from $n of ${input.size} elements: $input" }
      return emptyList()
    }
  }
}
