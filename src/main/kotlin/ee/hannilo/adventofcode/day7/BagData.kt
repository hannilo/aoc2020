package ee.hannilo.adventofcode.day7

data class BagData(
  val color: String,
  val container: Map<String, Int>
) {
  companion object {
    val BAG_REGEX = Regex("(\\w+ \\w+) bags contain (((\\d+ \\w+ \\w+ bags?)(, \\d+ \\w+ \\w+ bags?)*)|no other bags).")
  }
}
