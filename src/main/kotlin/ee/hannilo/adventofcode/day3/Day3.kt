package ee.hannilo.adventofcode.day3

import mu.KotlinLogging

class Day3 {

  companion object {
    private val logger = KotlinLogging.logger {}

    fun countTreesOnSlope(topology: TreeTopology, slope: TobogganSlope): Int {
      var position: TobogganSlopePosition
      val res = topology.rows.foldIndexed(0) { index, acc, _ ->
        position = TobogganSlopePosition(slope, index)
        if (position.posY <= topology.height && topology.valueAt(position.posX, position.posY) == TREE) {
          acc + 1
        } else {
          acc
        }
      }
      logger.info { "Counted $res trees on ${topology.height} rows using slope $slope" }
      return res
    }
  }

}
