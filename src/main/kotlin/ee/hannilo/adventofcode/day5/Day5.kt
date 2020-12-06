package ee.hannilo.adventofcode.day5

class Day5 {

  companion object {
    fun parse(list: List<String>): List<BoardingPass> {
      return list.map {
        BoardingPass(it)
      }
    }
  }

}
