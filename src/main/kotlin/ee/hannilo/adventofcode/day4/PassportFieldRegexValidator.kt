package ee.hannilo.adventofcode.day4

import mu.KotlinLogging

class PassportFieldRegexValidator : PassportFieldValidator() {
  override fun isValid(passport: Passport): Boolean {
    if (!super.isValid(passport)) {
      return false
    }
    val invalidFields = mutableMapOf<String, String>()
    REQUIRED_FIELDS.forEach { reqField ->
      if (!reqField.regex.matches(passport.fields[reqField.code]!!)) {
        invalidFields[reqField.code] = passport.fields[reqField.code]!!
      }
    }
    return if (invalidFields.isEmpty()) {
      true
    } else {
      logger.warn { "${invalidFields.size} invalid fields $invalidFields in $passport" }
      false
    }
  }

  companion object {
    private val logger = KotlinLogging.logger {}
  }
}
