package ee.hannilo.adventofcode.day2

import ee.hannilo.adventofcode.day2.policy.SingleCharPolicyMetadata

data class PasswordEntry(
  val password: String,
  val policyMetadata: SingleCharPolicyMetadata
) {
  companion object {
    @Deprecated("Use Day2.kt parser instead")
    fun fromDbString(str: String): PasswordEntry {
      str.split(' ').apply {
        val minMax = this[0].split('-')
        return PasswordEntry(
          password = this[1],
          policyMetadata = SingleCharPolicyMetadata(
            this[1][0],
            Integer.parseInt(minMax[0]),
            Integer.parseInt(minMax[1])
          )
        )
      }
    }
  }
}

