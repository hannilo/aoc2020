package ee.hannilo.adventofcode

import ee.hannilo.adventofcode.Util.Companion.readInputLines
import ee.hannilo.adventofcode.day4.Day4
import ee.hannilo.adventofcode.day4.PassportFieldRegexValidator
import ee.hannilo.adventofcode.day4.PassportFieldValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day4Test {

  private val input = readInputLines("Day4.txt")

  private val testInput = """
    ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
    byr:1937 iyr:2017 cid:147 hgt:183cm

    iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
    hcl:#cfa07d byr:1929

    hcl:#ae17e1 iyr:2013
    eyr:2024
    ecl:brn pid:760753108 byr:1931
    hgt:179cm

    hcl:#cfa07d eyr:2025 pid:166559648
    iyr:2011 ecl:brn hgt:59in
  """.trimIndent().lines()

  //SOLUTIONS
  @Test
  fun verifyPart1() {
    val validator = PassportFieldValidator()
    val passports = Day4.parsePassportList(testInput)
    val (valid, invalid) = Day4.validate(passports, validator)
    Assertions.assertEquals(2, valid.size)
    Assertions.assertEquals(2, invalid.size)
  }

  @Test
  fun part1() {
    val validator = PassportFieldValidator()
    val passports = Day4.parsePassportList(input)
    val (valid, invalid) = Day4.validate(passports, validator)
    Assertions.assertEquals(296, valid.size + invalid.size)
    Assertions.assertEquals(239, valid.size)
    Assertions.assertEquals(57, invalid.size)
  }

  @Test
  fun verifySolution2() {
    val validator = PassportFieldRegexValidator()
    val passports = Day4.parsePassportList(testInput)
    val (valid, invalid) = Day4.validate(passports, validator)
    Assertions.assertEquals(2, valid.size)
    Assertions.assertEquals(2, invalid.size)
  }

  @Test
  fun solution2() {
    val validator = PassportFieldRegexValidator()
    val passports = Day4.parsePassportList(input)
    val (valid, invalid) = Day4.validate(passports, validator)
    Assertions.assertEquals(296, valid.size + invalid.size)
    Assertions.assertEquals(188, valid.size)
    Assertions.assertEquals(108, invalid.size)
  }
  //END SOLUTIONS

  @Test
  fun parsesPassportLines() {
    val passports = Day4.parsePassportList(testInput)
    Assertions.assertEquals(4, passports.size)
    passports.forEach {
      println(it)
    }
    Assertions.assertEquals("59in", passports.last().fields["hgt"])
  }

  @Test
  fun parsesInput() {
    val passports = Day4.parsePassportList(input)
    Assertions.assertEquals(296, passports.size)
  }

  @Test
  fun partitionsPassports() {
    val validator = PassportFieldValidator()
    val passports = Day4.parsePassportList(testInput)
    val (valid, invalid) = Day4.validate(passports, validator)
    Assertions.assertEquals(2, valid.size)
    Assertions.assertEquals(2, invalid.size)
  }

  @Test
  fun validatesRegex_valid() {


    val validInput = """
      pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
      hcl:#623a2f

      eyr:2029 ecl:blu cid:129 byr:1989
      iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

      hcl:#888785
      hgt:164cm byr:2001 iyr:2015 cid:88
      pid:545766238 ecl:hzl
      eyr:2022

      iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
    """.trimIndent().lines().let {
      Day4.parsePassportList(it)
    }

    val (valid, invalid) = Day4.validate(validInput, PassportFieldRegexValidator())
    Assertions.assertEquals(4, valid.size)
    Assertions.assertEquals(0, invalid.size)
  }

  @Test
  fun validatesRegex_invalid() {
    val invalidInput = """
      eyr:1972 cid:100
      hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

      iyr:2019
      hcl:#602927 eyr:1967 hgt:170cm
      ecl:grn pid:012533040 byr:1946

      hcl:dab227 iyr:2012
      ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

      hgt:59cm ecl:zzz
      eyr:2038 hcl:74454a iyr:2023
      pid:3556412378 byr:2007
    """.trimIndent().lines().let {
      Day4.parsePassportList(it)
    }

    val (valid, invalid) = Day4.validate(invalidInput, PassportFieldRegexValidator())
    Assertions.assertEquals(4, invalid.size)
    Assertions.assertEquals(0, valid.size)
  }
}
