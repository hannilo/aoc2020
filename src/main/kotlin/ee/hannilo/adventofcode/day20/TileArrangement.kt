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
    println("searching ${width}x${width} in ${cornerQueue.map { it.id }}")
    val lists = mutableListOf<List<Tile>>()
    while (cornerQueue.isNotEmpty()) {
      val cornertile = cornerQueue.poll()
      println("searching: ${cornertile.id}")
      println(cornertile.toDisplay())
      val match = findMatch(listOf(cornertile), tileMap.minus(cornertile.baseId()))
      if (match.isNotEmpty()) {
        println("found list: ${match.flatMap { it.map { it.id } }}")
        //lists.add(match.flatten())
        return match //just find the first result, they're all equal, just flipped
      }
    }
    return lists.toList()
  }

  fun findMatch(currentList: List<Tile>, availableTiles: Map<String, Tile>): List<List<Tile>> {
    if (currentList.size == width*width) {
      return listOf(currentList)
    }
    println("${currentList.size}, avail: ${availableTiles.size} : $currentList")

    when {
      currentList.size % width == 0 -> {
        val top = currentList[currentList.size - width]
        val suitable =
          availableTiles.values.flatMap { tile -> mutations(tile).filter { mut -> mut.firstRow() == top.lastRow() } }
        val flat =  suitable.flatMap { findMatch(currentList + it, availableTiles.minus(it.baseId())) }
        println("${currentList.size} suitable:${suitable.size}, $suitable $flat")
        return flat
      }

      currentList.size < width -> {
        val left = currentList[currentList.size - 1]
        val suitable = availableTiles.values.flatMap { tile ->
          mutations(tile).filter { mut ->
            mut.firstCol() == left.lastCol()
          }
        }
        val flat =   suitable.flatMap { findMatch(currentList + it, availableTiles.minus(it.baseId())) }
        println("${currentList.size} suitable:${suitable.size}, $suitable $flat")
        return flat
      }

      else -> {
        val top = currentList[currentList.size - width]
        val left = currentList[currentList.size - 1]
        val suitable = availableTiles.values.flatMap { tile ->
          mutations(tile).filter { mut ->
            mut.firstRow() == top.lastRow() && mut.firstCol() == left.lastCol()
          }
        }
        val flat = suitable.flatMap { findMatch(currentList + it, availableTiles.minus(it.baseId())) }
        println("${currentList.size} suitable:${suitable.size}, $suitable $flat")
        return flat
      }
    }
  }

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


}
