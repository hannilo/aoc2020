package ee.hannilo.adventofcode.day2.policy

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class TobogganPolicy : SingleCharPolicy {
  override fun validate(password: String, policyMetadata: SingleCharPolicyMetadata): Boolean {
    if (policyMetadata.last > password.length) {
      println("warning: $password does not have $policyMetadata.last chars")
      return false
    }
    val res =
      (password[policyMetadata.first - 1] == policyMetadata.char) xor (password[policyMetadata.last - 1] == policyMetadata.char)
    logger.info {
      "Verifying $password, checking ${policyMetadata.char} at ${policyMetadata.first}-${policyMetadata.last}: " +
          "${password[policyMetadata.first - 1]}^${password[policyMetadata.last - 1]} : $res"
    }
    return res
  }
}
