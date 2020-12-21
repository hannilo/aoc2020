package ee.hannilo.adventofcode.day20

import java.util.*
import kotlin.math.roundToInt
import kotlin.math.sqrt

class TileArrangement(tiles: List<Tile>) {

  val tileMap = tiles.map { it.id to it }.toMap()
  val width = sqrt(tiles.size.toFloat()).roundToInt()

  fun search(): List<List<Tile>> {
    val cornerQueue = LinkedList(tileMap.values.flatMap { mutations(it) })
    //val cornerQueue = LinkedList( mutations(tileMap["1951"]!!))
    //val cornerQueue = LinkedList( listOf(tileMap["1951"]!!.flipY()))
    val lists = mutableListOf<List<Tile>>()
    while (cornerQueue.isNotEmpty()) {
      val cornertile = cornerQueue.poll()
      val match = findMatch(listOf(cornertile), tileMap.minus(cornertile.baseId()))
      if (match.isNotEmpty()) {
        //lists.add(match.flatten())
        return match //just find the first result, they're all equal, just flipped
      }
    }
    return lists.toList()
  }

  fun findMatch(currentList: List<Tile>, availableTiles: Map<String, Tile>): List<List<Tile>> {
    if (currentList.size == width * width) {
      return listOf(currentList)
    }
    when {
      currentList.size % width == 0 -> {
        val top = currentList[currentList.size - width]
        val suitable =
          availableTiles.values.flatMap { tile -> mutations(tile).filter { mut -> mut.firstRow() == top.lastRow() } }
        return suitable.flatMap { findMatch(currentList + it, availableTiles.minus(it.baseId())) }
      }

      currentList.size < width -> {
        val left = currentList[currentList.size - 1]
        val suitable = availableTiles.values.flatMap { tile ->
          mutations(tile).filter { mut ->
            mut.firstCol() == left.lastCol()
          }
        }
        return suitable.flatMap { findMatch(currentList + it, availableTiles.minus(it.baseId())) }
      }

      else -> {
        val top = currentList[currentList.size - width]
        val left = currentList[currentList.size - 1]
        val suitable = availableTiles.values.flatMap { tile ->
          mutations(tile).filter { mut ->
            mut.firstRow() == top.lastRow() && mut.firstCol() == left.lastCol()
          }
        }
        return suitable.flatMap { findMatch(currentList + it, availableTiles.minus(it.baseId())) }
      }
    }
  }


  companion object {
    fun mutations(tile: Tile): List<Tile> {
      return listOf(
        tile,
        tile.flipX(),
        tile.flipY(),
        tile.flipX().flipY(),
        tile.rotR(),
        tile.rotL(),
        tile.rotR().flipY(),
        tile.rotL().flipY(),
      )
    }

    /**
     * no validation
     * */
    fun merge(list: List<Tile>): Tile {
      val width = sqrt(list.size.toFloat()).roundToInt()
      val new = list.map { it.inner() }
        .windowed(width, width)
        .flatMap { l ->
          val init = l.first().rows.toMutableList()
          l.drop(1).fold(init) { acc, tile ->
            acc.let { it.forEachIndexed { i, s -> acc[i] = s + tile.row(i) } }
            acc
          }
        }
      return Tile(new, "merged")
    }

    val MONSTER_TOP = ".{18}#".toRegex()
    val MONSTER_MID = "#.{4}(##.{4}){2}#{3}".toRegex()
    val MONSTER_BOT = ".(#.{2}){5}#.{3}".toRegex()

    fun findMonsters(tile: Tile): Int {
      var count = 0
      val indices = mutableMapOf<Int, MutableList<Int>>()
      for (i in tile.rows.size - 1 downTo 2) {
        MONSTER_BOT.findAll(tile.row(i)).forEach {
          it.range.let { range ->
            if (MONSTER_MID.find(tile.row(i - 1), range.first) != null
              && MONSTER_TOP.find(tile.row(i - 2), range.first) != null
            ) {
              indices.merge(i, mutableListOf(range.first)) { old, new -> (old + new).toMutableList() }
              count++
            }
          }
        }
      }
      println("${tile.id} : $indices")
      return count
    }
  }


}
