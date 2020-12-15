package ee.hannilo.adventofcode.day14

import java.util.*

//faster to write a new one
class EmulatorV2(initialmask: String) {

  var memory = mutableMapOf<Long, Long>()
  var mask = initialmask


  fun maskAddresses(addr: String): MutableList<Long> {
    val masked = mask.mapIndexed { i, c ->
      if (c == '0') {
        addr[i]
      } else {
        c
      }
    }.joinToString(separator = "")

    val final = mutableListOf<Long>()
    val queue = LinkedList(listOf(masked))
    while (queue.isNotEmpty()) {
      val cur = queue.poll()
      if (cur.contains("X")) {
        queue.push(cur.replaceFirst('X', '1'))
        queue.push(cur.replaceFirst('X', '0'))
      } else {
        final.add(cur.toLong(2))
      }
    }
    return final
  }

  fun exec(str: String) {
    if (str.startsWith("mask")) {
      this.mask = str.substringAfter("= ")
    } else {
      Emulator.MEM_REGEX.matchEntire(str)!!.destructured.let { (addr, value) ->
        val addresses = maskAddresses(addr.toLong().toString(2).padStart(36, '0'))
        addresses.forEach {
          memory[it] = value.toLong()
        }
      }
    }
  }
}
