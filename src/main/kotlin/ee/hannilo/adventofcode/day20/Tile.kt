package ee.hannilo.adventofcode.day20

class Tile(val rows: List<String>, val id: String = "0") {

  fun baseId(): String {
    return id.substringBefore('-')
  }

  fun row(idx: Int): String {
    return rows[idx]
  }

  fun firstRow(): String {
    return rows.first()
  }

  fun lastRow(): String {
    return rows.last()
  }

  fun col(idx: Int): String {
    return (rows.map { it[idx] }).joinToString("")
  }

  fun firstCol(): String {
    return col(0)
  }

  fun lastCol(): String {
    return col(rows[0].length - 1)
  }

  fun flipX(): Tile {
    return Tile(
      rows.map { it.reversed() },
      "$id-X"
    )
  }

  fun flipY(): Tile {
    return Tile(rows.reversed(), "$id-Y")
  }

  fun rotR(): Tile {
    return Tile(
      rows[0].mapIndexed { index, _ ->
        rows.map { it[index] }.joinToString("").reversed()
      },
      "$id-R"
    )
  }

  fun rotL(): Tile {
    return Tile(
      rows[0].mapIndexed { index, _ ->
        rows.map { it[rows.size - index - 1] }.joinToString("")
      },
      "$id-L"
    )
  }

  fun inner(): Tile {
    return Tile(
      rows.drop(1).dropLast(1).map { it.drop(1).dropLast(1) },
      "$id-I"
    )
  }

  fun toDisplay(): String {
    return rows.joinToString("\n")
  }

  override fun toString(): String {
    return id
  }

  companion object {
    fun fromInput(list: List<String>): Tile {
      val id = "Tile (\\d+):".toRegex().matchEntire(list.first())?.destructured?.component1()!!
      return Tile(list.drop(1), id)
    }
  }

}
