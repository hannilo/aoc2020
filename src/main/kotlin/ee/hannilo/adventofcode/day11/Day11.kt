package ee.hannilo.adventofcode.day11

import kotlin.reflect.KFunction3

class Day11(input: String) {

  val board: List<List<Char>> = input.lines().map { it.toList() }
  val height = board.size
  val width = board.first().size

  var iteration = 0
  var alteredPrevious = true
  var state = board.map { it.map { it } }

  fun nextByNeighbors() {
    next(this::nextByNeighbor)
  }

  fun nextByVisible() {
    next(this::nextByVisible)
  }

  fun next(counter: KFunction3<Char, Int, Int, Char>): List<List<Char>> {
    alteredPrevious = false
    val next = state.map { it.map { it } }.mapIndexed { rIdx, row ->
      row.mapIndexed { cIdx, col ->
        val nextChar = counter(col, rIdx, cIdx)
        if (nextChar != col) alteredPrevious = true
        nextChar
      }
    }
    iteration++
    state = next
    return next
  }

  fun nextByNeighbor(curr: Char, row: Int, col: Int): Char {
    return when (curr) {
      'L' -> if (countNeighbors(row, col) == 0) '#' else 'L'
      '#' -> if (countNeighbors(row, col) >= 4) 'L' else '#'
      else -> '.'
    }
  }

  fun nextByVisible(curr: Char, row: Int, col: Int): Char {
    return when (curr) {
      'L' -> if (countVisible(row, col) == 0) '#' else 'L'
      '#' -> if (countVisible(row, col) >= 5) 'L' else '#'
      else -> '.'
    }
  }

  fun countVisible(row: Int, col: Int): Int {
    val viewDirections = listOf(
      Pair(-1, 1), //NE
      Pair(0, 1), // E
      Pair(1, 1), // SE
      Pair(1, 0), // S
      Pair(1, -1), // SW
      Pair(0, -1), // W
      Pair(-1, -1), // NW
      Pair(-1, 0), // N
    )
    val view = viewDirections.map { xyDiff ->
      var i = 1
      var check = Pair(row + (xyDiff.first * i), col + (xyDiff.second * i))
      while (check.first in 0 until height && check.second in 0 until width) {
        if (state[check.first][check.second] != NA) {
          break
        }
        i++
        check = Pair(row + (xyDiff.first * i), col + (xyDiff.second * i))
      }
      if (check.first !in 0 until height || check.second !in 0 until width) {
        'X'
      } else {
        state[check.first][check.second]
      }
      //println("$xyDiff * $i : ${state[check.first][check.second]}")
    }
    //println(view)
    return view.count { it == OCCUPIED }
  }

  fun countNeighbors(row: Int, col: Int): Int {
    val ignoreRow = when (row) {
      0 -> 0
      else -> 1
    }
    val ignoreCol = when (col) {
      0 -> 0
      else -> 1
    }
    return state.subList((row - 1).coerceAtLeast(0), (row + 1).coerceAtMost(height - 1) + 1)
      .mapIndexed { rIdx, r ->
        r.subList((col - 1).coerceAtLeast(0), (col + 1).coerceAtMost(width - 1) + 1)
          .filterIndexed { cIdx, c ->
            c == OCCUPIED && !(rIdx == ignoreRow && cIdx == ignoreCol)
          }
          .size
      }.fold(0) { acc, i -> acc + i }
  }

  fun getString(): String {
    return state.joinToString(separator = "\n") { it.joinToString(separator = "") }
  }

  companion object {
    val EMPTY = 'L'
    val OCCUPIED = '#'
    val NA = '.'
  }
}
