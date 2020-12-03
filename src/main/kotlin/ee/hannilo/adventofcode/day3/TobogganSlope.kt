package ee.hannilo.adventofcode.day3

data class TobogganSlope(
  val right: Int,
  val down: Int,
) {
  init {
    require(right >= 0) { "Right is not >= 0" }
    require(down > 0) { "Down is not > 0 " }
  }
}

data class TobogganSlopePosition(
  val slope: TobogganSlope,
  val step: Int = 0,
) {
  val posX = slope.right * step
  val posY = slope.down * step
}
