package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.day7.Day7
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day7Test {

  val searchColor = "shiny gold"

  val testInput = """
    light red bags contain 1 bright white bag, 2 muted yellow bags.
    dark orange bags contain 3 bright white bags, 4 muted yellow bags.
    bright white bags contain 1 shiny gold bag.
    muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
    shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
    dark olive bags contain 3 faded blue bags, 4 dotted black bags.
    vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
    faded blue bags contain no other bags.
    dotted black bags contain no other bags.
  """.trimIndent().lines()

  private val input = Util.readInputLines("Day7.txt")

  //SOLUTIONS
  @Test
  fun validatePart1() {
    val solver = Day7(testInput)
    val containers = solver.findContainers(searchColor)
    println(containers)
    Assertions.assertEquals(4, containers.size)
  }

  @Test
  fun part1() {
    val solver = Day7(input)
    val containers = solver.findContainers(searchColor)
    println(containers)
    Assertions.assertEquals(115, containers.size)
  }

  @Test
  fun validatePart2() {
    val solver = Day7(testInput)
    val children = solver.findChildren(searchColor)
    println(children)
    Assertions.assertEquals(32, children.values.reduce(Int::plus))
  }

  @Test
  fun validatePart2_2() {
    val solver = Day7("""
      shiny gold bags contain 2 dark red bags.
      dark red bags contain 2 dark orange bags.
      dark orange bags contain 2 dark yellow bags.
      dark yellow bags contain 2 dark green bags.
      dark green bags contain 2 dark blue bags.
      dark blue bags contain 2 dark violet bags.
      dark violet bags contain no other bags.
    """.trimIndent().lines())
    val children = solver.findChildren(searchColor)
    println(children)
    Assertions.assertEquals(126, children.values.reduce(Int::plus))
  }

  @Test
  fun part2() {
    val solver = Day7(input)
    val children = solver.findChildren(searchColor)
    println(children)
    Assertions.assertEquals(1250, children.values.reduce(Int::plus))
  }
  //END SOLUTIONS

  @Test
  fun parsesBagRow() {
    val solver = Day7(testInput)
    val bags = solver.bags
    println(bags)
    Assertions.assertEquals(9, bags.size)
  }
}
