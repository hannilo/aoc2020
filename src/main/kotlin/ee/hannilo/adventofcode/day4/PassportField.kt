package ee.hannilo.adventofcode.day4

enum class PassportField(
  val code: String,
  val description: String,
  val regex: Regex
  ) {
  //could also handle value validation with callable override methods
  BYR("byr", "Birth Year", Regex("(19[2-9][0-9]|200[0-2])")),
  IYR("iyr", "Issue Year", Regex("20(1[0-9]|20)")),
  EYR("eyr", "Expiration Year", Regex("20(2[0-9]|30)")),
  HGT("hgt", "Height", Regex("((1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-6])in))")),
  HCL("hcl", "Hair Color", Regex("#[a-f0-9]{6}")),
  ECL("ecl", "Eye Color", Regex("(amb|blu|brn|grn|gry|hzl|oth)")),
  PID("pid", "Passport ID", Regex("\\d{9}")),
  CID("cid", "Country ID", Regex(".*"));
}
