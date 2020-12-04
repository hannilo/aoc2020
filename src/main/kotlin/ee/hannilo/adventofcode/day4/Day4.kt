package ee.hannilo.adventofcode.day4

import java.lang.IllegalArgumentException

const val PASSPORT_LINE_REGEX = "((\\S{3}:\\S+) ?)+"


class Day4 {

  companion object {

    fun parsePassportList(list: List<String>): List<Passport> {
      val passportList = mutableListOf<Passport>()
      val currentPassportStrings = mutableListOf<String>()

      list.forEachIndexed { idx, inputLine ->
        if (inputLine.isBlank() || idx == list.size - 1) {
          if (inputLine.isNotBlank()) {
            currentPassportStrings.add(inputLine)
          }
          passportList.add(parsePassport(currentPassportStrings.flatMap { it.split(' ') }))
          currentPassportStrings.clear()
        } else {
          if (PASSPORT_LINE_REGEX.toRegex().matchEntire(inputLine) == null) {
            throw IllegalArgumentException("Failed to parse line $idx '$inputLine' as passport info row")
          }
          currentPassportStrings.add(inputLine)
        }
      }
      return passportList
    }

    fun parsePassport(list: List<String>): Passport {
      return Passport(list.associate { kv ->
        val (key, value) = kv.split(":")
        key to value
      })
    }

    fun validate(passports: List<Passport>, validator: PassportValidator): Pair<List<Passport>, List<Passport>> {
      return passports.partition {
        validator.isValid(it)
      }
    }
  }
}
