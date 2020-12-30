package ee.hannilo.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import java.lang.IllegalStateException
import org.junit.jupiter.api.Test

/**
 * could use
 *
 * https://en.wikipedia.org/wiki/Fermat%27s_little_theorem
 *
 * and
 *
 * https://en.wikipedia.org/wiki/Baby-step_giant-step
 *
 * but brute force is too quick to bother
 * */
class Day25Test {

  val testCardPub = 5764801L
  val testDoorPub = 17807724L

  val cardPub = 17607508L
  val doorPub = 15065270L


  fun transform(subject: Long, loop: Int): Long {
    var value = 1L
    repeat(loop) {
      value *= subject
      value %= DIVISOR
    }
    println("$subject : $loop $value")
    return value
  }

  fun searchForLoop(public: Long): Int {
    var loop = 0
    var value = 1L
    while (true) {
      loop++
      value *= 7
      value %= DIVISOR
      if (value == public) return loop
      if (loop > 100_000_000) {
        throw IllegalStateException("Max 100_000_000 tries reached")
      }
    }
  }


  @Test
  fun validatePart1() {
    val cardLoop = searchForLoop(testCardPub)
    assertEquals(8, cardLoop)
    val doorLoop = searchForLoop(testDoorPub)
    assertEquals(11, doorLoop)

    val encKey = 14897079L
    assertEquals(transform(testDoorPub, cardLoop), encKey)
    assertEquals(transform(testCardPub, doorLoop), encKey)
  }

  @Test
  fun part1() {
    val cardLoop = searchForLoop(cardPub)
    println("cardLoop : $cardLoop")
    val doorLoop = searchForLoop(doorPub)
    println("doorLoop : $doorLoop")

    assertEquals(transform(doorPub, cardLoop), transform(cardPub, doorLoop))
  }

  companion object {
    val DIVISOR = 20201227L
  }
}
