package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.Util.Companion.chunkListByBlanks
import ee.hannilo.adventofcode.Util.Companion.gcd
import ee.hannilo.adventofcode.Util.Companion.lcm
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
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

  @Test
  fun gcd_primes() {
    val gcd = gcd(7, 3)
    assertEquals(1, gcd)
  }

  @Test
  fun gcd_order() {
    assertEquals(7, gcd(21, 14))
    assertEquals(7, gcd(14, 21))
  }

  @Test
  fun lcm_prime() {
    assertEquals(21, lcm(3, 7))
    assertEquals(35, lcm(5, 7))
    assertEquals(77, lcm(11, 7))
  }

  @Test
  fun lcm_nonprime() {
    assertEquals(10, lcm(2, 5))
    assertEquals(10, lcm(5, 2))
    assertEquals(12, lcm(4, 6))
  }
}
