package ee.hannilo.adventofcode.day8

data class Operation(
  val instruction: Instruction,
  val argument: Int = 0,
) {
  companion object {
    val OPERATION_REGEX = Regex("(nop|acc|jmp) ([+-]\\d+)")
  }
}
