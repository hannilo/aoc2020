package ee.hannilo.adventofcode.day5

import java.lang.IllegalArgumentException
import kotlin.math.pow

data class BoardingPass(
    val str: String,
    var row: Int = 0, //hack to get toString to print this
    var col: Int = 0,
    var id: Int = 0,
) {

  init {
    if (BOARDING_PASS_REGEX.matchEntire(str) == null) {
      throw IllegalArgumentException("$str does not match ${BOARDING_PASS_REGEX.pattern}")
    }

    //a cleaner solution would be to replace the chars and use String::toInt(base)
    row = str.substring(0, 7).foldIndexed(0) { index, acc, c ->
      if (c == 'B') {
        acc + 2.toDouble().pow(6 - index).toInt()
      } else {
        acc
      }
    }

    col = str.substring(7).foldIndexed(0) { index, acc, c ->
      if (c == 'R') {
        acc + 2.toDouble().pow(2 - index).toInt()
      } else {
        acc
      }
    }

    //basically shift left and OR
    id = row * 8 + col
  }

  companion object {
    val BOARDING_PASS_REGEX = Regex("[FB]{7}[RL]{3}")
  }
}
