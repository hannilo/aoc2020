package ee.hannilo.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

//another quick and dirty solution
class Day21Test {

  private val testinput = """
    mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
    trh fvjkl sbzzf mxmxvkd (contains dairy)
    sqjhc fvjkl (contains soy)
    sqjhc mxmxvkd sbzzf (contains fish)
  """.trimIndent().lines()

  private val input = Util.readInputLines("Day21.txt")

  class Allergen(val name: String) {
    var candidates = mutableSetOf<String>()

    override fun toString(): String {
      return "$name:$candidates"
    }
  }

  private val INGREDIENT_REGEX = "(([a-z]+ )+)\\(contains (.+)\\)".toRegex() //good enough
  fun parse(string: String): List<Allergen> {
    INGREDIENT_REGEX.matchEntire(string)?.destructured?.let { (c, _, i, _) ->
      val candidates = c.trim().split(" ")
      val allergen = i.trim().split(", ")
      return allergen.map { name -> Allergen(name).apply { this.candidates.addAll(candidates) } }
    }
    throw IllegalArgumentException("$string does not match")
  }

  fun findPossibilities(parsed: List<List<Allergen>>): Map<String, Allergen> {
    return parsed.fold(mutableMapOf<String, Allergen>()) { acc, list ->
      list.forEach {
        acc.merge(it.name, it) { ex, new ->
          Allergen(ex.name).apply { candidates = ex.candidates.intersect(new.candidates).toMutableSet() }
        }
      }
      acc
    }.toMap()
  }

  fun findNotMatching(parsed: List<List<Allergen>>): List<String> {
    val possibilities = findPossibilities(parsed)
    val candidates = parsed.flatMap { list -> list.flatMap { it.candidates } }.toSet()
    return candidates.filter { c -> possibilities.none { (_, allergen) -> allergen.candidates.contains(c) } }
  }

  @Test
  fun validatePart1() {
    val parsed = testinput.map { parse(it) }
    val notMatching = findNotMatching(parsed)
    val res = notMatching.fold(0) { acc, nm -> acc + testinput.joinToString(" ").split(" ").count() { it == nm } }
    assertEquals(5, res)
  }

  @Test
  fun part1() {
    val parsed = input.map { parse(it) }
    val notMatching = findNotMatching(parsed)
    val res = notMatching.fold(0) { acc, nm -> acc + input.joinToString(" ").split(" ").count { it.trim() == nm } }
    assertEquals(2517, res)
  }

  @Test
  fun validatePart2() {
    val parsed = testinput.map { parse(it) }
    val possibilities = findPossibilities(parsed).map { it.value }.sortedBy { it.candidates.size }
    val used = mutableSetOf<String>()
    possibilities.forEach {
      it.candidates.removeAll(used)
      if (it.candidates.size > 1) throw IllegalStateException("Too many candidates $it")
      used.addAll(it.candidates)
    }
    val res = possibilities.sortedBy { it.name }.map { it.candidates.first() }.joinToString(",").also { println(it) }
    assertEquals("mxmxvkd,sqjhc,fvjkl", res)
  }

  @Test
  fun part2() {
    val parsed = input.map { parse(it) }
    val possibilities = findPossibilities(parsed).map { it.value }.sortedBy { it.candidates.size }
    val used = mutableSetOf<String>()
    while (possibilities.map { it.candidates.size }.reduce(Int::plus) > possibilities.size) {
      possibilities.forEach {
        if (it.candidates.size == 1) used.addAll(it.candidates) else it.candidates.removeAll(used)
      }
    }
    val res = possibilities.sortedBy { it.name }.map { it.candidates.first() }.joinToString(",").also { println(it) }
    assertEquals("rhvbn,mmcpg,kjf,fvk,lbmt,jgtb,hcbdb,zrb", res)
  }

  @Test
  fun parsesLine() {
    val res = parse(testinput.first())
    println(res)
  }
}
