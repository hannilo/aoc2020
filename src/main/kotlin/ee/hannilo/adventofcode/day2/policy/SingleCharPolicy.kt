package ee.hannilo.adventofcode.day2.policy

interface SingleCharPolicy {
  fun validate(password: String, policyMetadata: SingleCharPolicyMetadata): Boolean
}
