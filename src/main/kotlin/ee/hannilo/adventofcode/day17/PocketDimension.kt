package ee.hannilo.adventofcode.day17

class PocketDimension<T : ConwayCube>(initialCubes: List<T>, private val factoryFn: (String) -> ConwayCube) {

  var cubeMap: MutableMap<String, ConwayCube> = initialCubes.map { it.key to it }.toMap().toMutableMap()

  fun cycle() {
    val cubes = cubeMap.keys.toSet()
    val neighbors = mutableMapOf<String, Int>()
    cubeMap.values.forEach {
      it.neighbors.forEach { n ->
        neighbors.merge(n.key, 1) { t, u -> t + u }
      }
    }
    //probably should merge this
    cubeMap = cubes.filter { neighbors[it] in 2..3 }.map { it to factoryFn(it) }.toMap().toMutableMap()
    neighbors.filter { it.value == 3 && !cubes.contains(it.key) }.forEach { cubeMap[it.key] = factoryFn(it.key) }
    println("${cubeMap.size} : $cubeMap")
  }

  companion object {
    fun parse3d(list: List<String>): List<ConwayCube3D> {
      val res = mutableListOf<ConwayCube3D>()
      list.mapIndexed() { rowIdx, row ->
        row.forEachIndexed { colIdx, col ->
          if (col == '#') {
            res.add(ConwayCube3D(colIdx, rowIdx, 0))
          }
        }
      }
      return res
    }

    fun parse4d(list: List<String>): List<ConwayCube4D> {
      val res = mutableListOf<ConwayCube4D>()
      list.mapIndexed() { rowIdx, row ->
        row.forEachIndexed { colIdx, col ->
          if (col == '#') {
            res.add(ConwayCube4D(colIdx, rowIdx, 0, 0))
          }
        }
      }
      return res
    }
  }
}
