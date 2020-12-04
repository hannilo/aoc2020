package ee.hannilo.adventofcode.day2.policy

/**
 * Checks if occurrence of the policy char is within the policy range
 * */
class SledPolicy: SingleCharPolicy {
  override fun validate(password: String, policyMetadata: SingleCharPolicyMetadata): Boolean {
    val count = password.count { it == policyMetadata.char }
    return count in policyMetadata.first..policyMetadata.last
  }
}
