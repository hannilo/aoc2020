package ee.hannilo.adventofcode.day9

import java.util.*

data class Node(
  val value: Long,
  val sumWithParent: Long?,
  val children: LinkedList<Node> = LinkedList()
) {
}
