package ee.hannilo.adventofcode.day8

enum class Instruction(val code: String) {
  NOP("nop"),
  ACC("acc"),
  JMP("jmp");

  companion object {
    private val map = values().associateBy(Instruction::code)
    fun fromCode(code: String) = map[code]!!
  }
}
