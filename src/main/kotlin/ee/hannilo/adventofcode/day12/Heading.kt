package ee.hannilo.adventofcode.day12

enum class Heading(val hdg: Int) {
  N(0),
  E(90),
  S(180),
  W(270);

  companion object {
    private val map = values().associateBy(Heading::hdg)
    fun fromHdg(hdg: Int) = map[hdg % 360]!!
  }
}
