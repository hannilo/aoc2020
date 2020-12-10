package ee.hannilo.adventofcode.day10

import mu.KotlinLogging

class Day10(input: List<Int>) {

  val adapters = input.sorted()

  init {
    logger.debug { "${adapters.size} : $adapters" }
  }

  fun buildTree() {

  }

  companion object {
    val logger = KotlinLogging.logger {}
  }
}
