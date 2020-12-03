package ee.hannilo.adventofcode.day2.policy

interface SingleCharPolicy {
  /**
   * Validates the given password according to the provided metadata
   * */
  fun validate(password: String, policyMetadata: SingleCharPolicyMetadata): Boolean
}
