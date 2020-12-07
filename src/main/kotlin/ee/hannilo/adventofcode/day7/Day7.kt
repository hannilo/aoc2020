package ee.hannilo.adventofcode.day7

import mu.KotlinLogging
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Uses BFS to find containing/contained bags
 * */
class Day7(list: List<String>) {

  val bags = mutableMapOf<String, BagData>()

  init {
    load(list)
  }

  private fun load(list: List<String>): List<BagData> {
    list.forEach {
      val bag = parse(it)
      bags[bag.color] = bag
    }
    logger.info { "Loaded ${bags.size} bags" }
    return bags.values.toList()
  }

  fun findContainers(searchColor: String): Set<String> {
    val searchQueue = LinkedList(getContainingColors(searchColor))
    val ancestors = searchQueue.toMutableSet()
    val visited = mutableSetOf<String>()
    logger.debug { "Found ${searchQueue.size} bags containing $searchColor : $searchQueue" }
    var iterations = 1

    while (searchQueue.isNotEmpty()) {
      val currentColor = searchQueue.poll()
      visited.add(currentColor)
      val currentContainers = getContainingColors(currentColor)
      logger.debug { "Found ${currentContainers.size} bags containing $currentColor : $currentContainers, " +
          "remaining queue: ${searchQueue.size}, visited ${visited.size}" }
      iterations += 1
      ancestors.addAll(currentContainers)
      searchQueue.addAll(currentContainers.filter {
        it != searchColor && !searchQueue.contains(it) && !visited.contains(it)
      })
    }
    logger.info { "Found ${ancestors.size} containers in $iterations queries" }
    return ancestors
  }

  fun findChildren(searchColor: String): Map<String, Int> {
    val bag = bags[searchColor]!!
    val searchQueue = LinkedList(bag.container.entries.map { BagCount(it.key, it.value) })
    val childMap = bag.container.toMutableMap()
    logger.debug { "$searchColor contains $childMap, searching: $searchQueue" }
    var iterations = 1

    while (searchQueue.isNotEmpty()) {
      iterations += 1
      val currentSearch = searchQueue.poll()
      val currentBag = bags[currentSearch.color]!!
      logger.debug { "${currentBag.color} contains ${currentBag.container}" }

      //if there already is an entry in the queue, add the count to its tally
      //cuts processing down by ~half
      currentBag.container.entries.forEach { (color, childCount) ->
        if (searchQueue.any { it.color == color }) {
          searchQueue.find { it.color == color }?.let { it.count += currentSearch.count * childCount }
        } else {
          searchQueue.add(BagCount(color, currentSearch.count * childCount))
        }
      }

      logger.debug { "${searchQueue.size}: $searchQueue" }
      currentBag.container.forEach { (color, count) ->
        childMap[color] = childMap[color]?.let { it + currentSearch.count * count } ?: currentSearch.count * count
      }
    }

    logger.info { "Found ${childMap.values.reduce(Int::plus)} descendants in $iterations queries" }
    return childMap
  }

  private fun getContainingColors(color: String): Set<String> {
    return bags.filter { it.value.container.containsKey(color) }.keys
  }


  companion object {
    private val logger = KotlinLogging.logger {}

    fun parse(str: String): BagData {
      if (BagData.BAG_REGEX.matchEntire(str) == null) {
        throw IllegalArgumentException("$str is not a valid bag row.")
      }
      val color = getColor(str)
      val container = str.substringAfter("contain ")
        .substringBefore(".")
        .split(",")
        .map { it.trim() }
        .filter { it != "no other bags" }
        .map {
          getColor(it) to getCount(it)
        }.toMap()

      val bag = BagData(color, container)
      return bag
    }

    private fun getColor(str: String): String {
      return str.substringBefore(" bag").replace(Regex("^\\d+ "), "")
    }

    private fun getCount(str: String): Int {
      return str.substringBefore(" ").toInt()
    }
  }
}
