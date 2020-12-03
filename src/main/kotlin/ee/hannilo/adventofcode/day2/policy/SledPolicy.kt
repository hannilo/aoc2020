package ee.hannilo.adventofcode.day2.policy

class SledPolicy: SingleCharPolicy {
  override fun validate(password: String, policyMetadata: SingleCharPolicyMetadata): Boolean {
    val countInStr = password.fold(0) { acc, ch ->
      if (ch == policyMetadata.char) acc + 1 else acc
    }
    return countInStr in policyMetadata.first..policyMetadata.last
  }
}
