package ee.hannilo.adventofcode.day8

data class ComputationResult(
  val acc: Int,
  val lastOperation: Operation,
  val lastPointer: Int,
  val nextPointer: Int,
  val finished: Boolean = false
)
