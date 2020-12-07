package ee.hannilo.adventofcode

import java.io.File

const val INPUT_DIR = "input"

class Util {
  companion object {
    fun readInputFile(file: String): List<String> {
      return File("${INPUT_DIR}/${file}").readText().trim().lines()
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
  }
}
