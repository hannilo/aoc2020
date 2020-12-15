package ee.hannilo.adventofcode.day15

class Game(init: String) {

  val memory = init.split(",").mapIndexed { idx, i -> i.toInt() to idx + 1 }.toMap().toMutableMap()

  var completedTurn = memory.size
  var previous = init.split(",").last().toInt()

  fun next() {
    val next = memory[previous]?.let { completedTurn - it } ?: 0
    memory[previous] = completedTurn
    previous = next
    completedTurn++
  }
}
