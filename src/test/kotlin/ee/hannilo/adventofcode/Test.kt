package ee.hannilo.adventofcode

import org.junit.jupiter.api.Test

class Test {

  @Test
  fun fib() {
    (0..10).fold(0 to 1) { (prev, curr), _ ->
      println(prev)
      curr to (prev + curr)
    }

    println()

    var delta = 1
    var tmp = 0
    var curr = 0
    (0..10).forEach { _ ->
      println(curr)
      tmp = curr
      curr += delta
      delta = tmp
    }

    println()

    (0..10).forEach {
      println(recursFib(it))
    }

    println()

    (0..10).forEach {
      println(recFib(it))
    }
  }

  private tailrec fun recursFib(n: Int, prev: Int = 0, curr: Int = 1): Int {
    return when (n) {
      0 -> prev
      1 -> curr
      else -> recursFib(n - 1, curr, prev + curr)
    }
  }

  private fun recFib(n: Int): Int {
    return when (n) {
      0 -> 0
      1 -> 1
      else -> (recFib(n - 1) + recFib(n - 2))
    }
  }

}
