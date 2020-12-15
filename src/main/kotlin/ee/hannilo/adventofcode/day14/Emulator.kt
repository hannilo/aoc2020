package ee.hannilo.adventofcode.day14

class Emulator(mask: String) {

  var memory = mutableMapOf<Int, Long>()
  var posMask: Long = -1L
  var negMask: Long = -1L

  init {
    setMask(mask)
  }

  fun setMask(mask: String) {
    this.posMask = mask.replace('X', '0').toLong(2)
    this.negMask = mask.replace('X', '1').toLong(2)
    println("masks: +${posMask.toString(2)} -${negMask.toString(2)}")
  }

  fun exec(str: String) {
    if (str.startsWith("mask")) {
      setMask(str.substringAfter("= "))
    } else {
      MEM_REGEX.matchEntire(str)!!.destructured.let { (addr, value) ->
        val actual = value.toLong().or(posMask).and(negMask)
        println("writing to $addr: $value -> $actual")
        memory[addr.toInt()] = actual
      }
    }
  }

  companion object {
    val MEM_REGEX = Regex("mem\\[(\\d+)] = (\\d+)")
  }

}
