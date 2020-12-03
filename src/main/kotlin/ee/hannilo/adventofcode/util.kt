package ee.hannilo.adventofcode

import java.io.File

const val INPUT_DIR = "input"

class Util {
  companion object {
    fun readInputFile(file: String): List<String> {
      return File("${INPUT_DIR}/${file}").readLines()
    }
  }
}
