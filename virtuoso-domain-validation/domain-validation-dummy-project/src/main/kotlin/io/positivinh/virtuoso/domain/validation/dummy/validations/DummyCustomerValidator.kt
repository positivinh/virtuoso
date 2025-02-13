package io.positivinh.virtuoso.domain.validation.dummy.validations

import io.positivinh.virtuoso.domain.validation.dummy.model.EntityToValidate
import io.positivinh.virtuoso.domain.validations.SimpleValidator
import org.apache.commons.lang3.StringUtils
import org.springframework.validation.Errors

/**
 * This custom validator validates if the property [] contains the token []
 */
class DummyCustomerValidator : SimpleValidator<EntityToValidate> {

    companion object {
        const val CUSTOM_VALIDATOR_TOKEN = "valid"
    }

    override fun validate(entity: EntityToValidate, errors: Errors) {

        val propertyValidatedByCustomValidator = entity.propertyValidatedByCustomValidator

        if (!StringUtils.contains(propertyValidatedByCustomValidator, CUSTOM_VALIDATOR_TOKEN)) {
          errors.rejectValue("propertyValidatedByCustomValidator", "CustomValidation")
        }
    }
}
