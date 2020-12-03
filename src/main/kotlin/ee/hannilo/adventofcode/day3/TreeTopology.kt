package ee.hannilo.adventofcode.day3

import java.lang.IllegalArgumentException

const val TREE_TOPOLOGY_REGEX = "[.#]+"
const val TREE = '#'
const val BLANK = '.'

class TreeTopology(val rows: List<String>) {

  val height: Int
  val width: Int

  init {
    require(rows.isNotEmpty()) { "Rows must not be empty" }
    val regex = TREE_TOPOLOGY_REGEX.toRegex()
    rows.reduceIndexed { index, acc, s ->
      if (acc.length != s.length) {
        throw IllegalArgumentException(
          "Topology parse failure at ${index + 1} of ${rows.size}: '$s' length (${s.length}) " +
              "differs from previous '$acc' (${acc.length})"
        )
      }
      if (regex.matchEntire(s) == null) {
        throw IllegalArgumentException(
          "Topology parse failure at ${index + 1} of ${rows.size}: " +
              "'$s' does not match regex '${TREE_TOPOLOGY_REGEX}'"
        )
      }
      s
    }
    height = rows.size
    width = rows[0].length
  }

  fun valueAt(stepX: Int, stepY: Int): Char {
    println(
      "Computed row $stepY: [${
        rows[stepY].repeat((stepX / width) + 1).toCharArray().let {
          it[stepX] = if (it[stepX] == TREE) 'X' else 'O'
          String(it)
        }
      }] idx $stepX"
    )
    return rows[stepY][stepX % width]
  }
}
