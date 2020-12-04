package ee.hannilo.adventofcode.day2

import ee.hannilo.adventofcode.day2.policy.SingleCharPolicy
import ee.hannilo.adventofcode.day2.policy.SingleCharPolicyMetadata
import mu.KotlinLogging
import java.lang.IllegalArgumentException

class Day2 {

  companion object {
    private val logger = KotlinLogging.logger {}

    private val regex = Regex("(\\d+)-(\\d+) (\\S): (\\S+)")

    fun parseEntry(dbEntry: String): PasswordEntry {
      return regex.matchEntire(dbEntry)
        ?.destructured
        ?.let { (first, last, char, password) ->
        PasswordEntry(password, SingleCharPolicyMetadata(char.first(), first.toInt(), last.toInt()))
      } ?: throw IllegalArgumentException("Failed to parse $dbEntry")
    }

    fun getValids(entries: List<PasswordEntry>, policy: SingleCharPolicy): List<PasswordEntry> {
      val res = entries.filter {
        policy.validate(it.password, it.policyMetadata)
      }
      logger.info { "Found ${res.size} valids in ${entries.size} entries" }
      return res
    }
  }
}
