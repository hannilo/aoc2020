package ee.hannilo.adventofcode.day12

class Ferry {

  var heading = Heading.E

  /**
   * easting, nording
   * */
  var position = Pair(0, 0)

  var waypoint = Pair(10, 1)

  fun nextWaypoint(str: String) {
    when {
      TURN_REGEX.matchEntire(str) != null -> {
        rotateWp(str)
      }
      TRANSLATE_REGEX.matchEntire(str) != null -> {
        translateWp(str)
      }
      FORWARD_REGEX.matchEntire(str) != null -> {
        moveToWp(str)
      }
      else -> {
        throw IllegalArgumentException("Invalid move $str")
      }
    }
  }

  private fun moveToWp(str: String) {
    val times = str.drop(1).toInt()
    repeat(times) {
      this.position = Pair(
        this.position.first + this.waypoint.first,
        this.position.second + this.waypoint.second
      )
    }
  }

  /**
   * simple 90deg rotations, no point in using a rotation matrix
   * */
  private fun rotateWp(str: String) {
    val times = str.drop(1).toInt() / 90
    repeat(times) {
      waypoint = when (str.first()) {
        'R' -> Pair(this.waypoint.second, -this.waypoint.first)
        'L' -> Pair(-this.waypoint.second, this.waypoint.first)
        else -> throw IllegalArgumentException("Invalid turn $str")
      }
    }
  }

  private fun translateWp(str: String) {
    val dir = str.first()
    val magnitude = str.drop(1).toInt()
    waypoint = Pair(
      waypoint.first + (TRANSLATE[dir]!!.first * magnitude),
      waypoint.second + (TRANSLATE[dir]!!.second * magnitude)
    )
  }


  fun nextPosition(str: String) {
    when {
      TURN_REGEX.matchEntire(str) != null -> {
        turn(str)
      }
      TRANSLATE_REGEX.matchEntire(str) != null -> {
        translate(str)
      }
      FORWARD_REGEX.matchEntire(str) != null -> {
        forward(str)
      }
      else -> {
        throw IllegalArgumentException("Invalid move $str")
      }
    }
  }

  private fun turn(str: String) {
    var newHdg = str.replace('R', '+').replace('L', '-').toInt()
    while (newHdg < 0) newHdg += 360
    this.heading = Heading.fromHdg(this.heading.hdg + newHdg)
  }

  private fun translate(str: String) {
    val magnitude = str.drop(1).toInt()
    move(str.first(), magnitude)
  }

  private fun forward(str: String) {
    move(heading.name.first(), str.drop(1).toInt())
  }

  private fun move(dir: Char, magnitude: Int) {
    position = Pair(
      position.first + (TRANSLATE[dir]!!.first * magnitude),
      position.second + (TRANSLATE[dir]!!.second * magnitude)
    )
  }

  companion object {
    val TURN_REGEX = Regex("[RL](90|180|270)")
    val TRANSLATE_REGEX = Regex("[NSEW]\\d+")
    val FORWARD_REGEX = Regex("F\\d+")
    val TRANSLATE = mapOf(
      'N' to Pair(0, 1),
      'E' to Pair(1, 0),
      'S' to Pair(0, -1),
      'W' to Pair(-1, 0),
    )
  }
}
