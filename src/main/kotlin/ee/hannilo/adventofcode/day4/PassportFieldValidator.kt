package ee.hannilo.adventofcode.day4

import mu.KotlinLogging



open class PassportFieldValidator: PassportValidator {
  override fun isValid(passport: Passport): Boolean {
    val missingFields = mutableListOf<String>()
    REQUIRED_FIELDS.forEach { reqField ->
      if (!passport.fields.containsKey(reqField.code)) {
        missingFields.add(reqField.code)
      }
    }
    return if (missingFields.isEmpty()) {
      true
    } else {
      logger.warn { "Missing ${missingFields.size}/${REQUIRED_FIELDS.size} fields $missingFields in $passport"}
      false
    }
  }

  companion object {
    private val logger = KotlinLogging.logger {}

    val REQUIRED_FIELDS = listOf(
      PassportField.BYR,
      PassportField.IYR,
      PassportField.EYR,
      PassportField.HGT,
      PassportField.HCL,
      PassportField.ECL,
      PassportField.PID,
      //PassportField.CID, //not needed
    )
  }
}
