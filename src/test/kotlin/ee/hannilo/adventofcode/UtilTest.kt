package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.Util.Companion.chunkListByBlanks
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UtilTest {

  @Test
  fun chunksByBlanks() {
    val input = """
      one
      two
      
      three
      
      four
      five
      six
    """.trimIndent().lines()

    val chunkedLines = chunkListByBlanks(input)
    Assertions.assertEquals(3, chunkedLines.size)
    Assertions.assertEquals(listOf("three"), chunkedLines[1])
  }
}
