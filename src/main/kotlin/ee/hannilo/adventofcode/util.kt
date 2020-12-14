package ee.hannilo.adventofcode

import java.io.File

const val INPUT_DIR = "input"

class Util {
  companion object {

    fun readInputFile(file: String): String {
      return File("${INPUT_DIR}/${file}").readText().trim()
    }

    fun readInputLines(file: String): List<String> {
      return readInputFile(file).lines()
    }

    fun chunkListByBlanks(list: List<String>): List<List<String>> {
      val result = mutableListOf(mutableListOf<String>())
      list.forEach { s ->
        if (s.isBlank()) {
          result.add(mutableListOf())
        } else {
          result.last().add(s)
        }
      }
      return result.toList()
    }

    fun gcd(a: Long, b: Long): Long {
      var gcd = if (a > b) b else a
      while (gcd <= a && gcd <= b) {
        if (a % gcd == 0L && b % gcd == 0L) return gcd
        --gcd
      }
      return gcd
    }

    fun gcd(a: Int, b: Int): Int {
      return gcd(a.toLong(), b.toLong()).toInt()
    }

    fun lcm(a: Long, b: Long): Long {
      val gcd = gcd(a, b)
      return a * b / gcd
    }

    fun lcm(a: Int, b: Int): Int {
      return lcm(a.toLong(), b.toLong()).toInt()
    }
  }
}
