package ee.hannilo.adventofcode.day24

/**
 * Hex tile in cubic coordinates
 * @see <a href="https://www.redblobgames.com/grids/hexagons/#coordinates-cube">Hexagonal coordinates</a>
 * */
class HexTile(
  var x: Int,
  var y: Int,
  var z: Int,
) {
  
  fun getNeighbors(): List<HexTile> {
    return listOf(
      this.clone().apply { x++;z-- },
      this.clone().apply { x++;y-- },
      this.clone().apply { y--;z++ },
      this.clone().apply { x--;z++ },
      this.clone().apply { x--;y++ },
      this.clone().apply { y++;z-- },
    )
  }

  private fun clone(): HexTile {
    return HexTile(x, y, z)
  }

  override fun toString(): String {
    return "Hex($x,$y,$z)"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as HexTile

    if (x != other.x) return false
    if (y != other.y) return false
    if (z != other.z) return false

    return true
  }

  override fun hashCode(): Int {
    var result = x
    result = 31 * result + y
    result = 31 * result + z
    return result
  }

  companion object {
    fun parse(string: String): HexTile {
      var x = 0
      var y = 0
      var z = 0

      "([ns]?[ew])".toRegex().findAll(string)
          .map { it.groupValues[0] }.toList()
          .forEach {
            when (it) {
              "ne" -> {
                x++; z--
              }
              "e" -> {
                x++; y--
              }
              "se" -> {
                y--;z++
              }
              "sw" -> {
                x--;z++
              }
              "w" -> {
                x--;y++
              }
              "nw" -> {
                y++;z--
              }
            }
          }

      return HexTile(x, y, z)
    }
  }
}
