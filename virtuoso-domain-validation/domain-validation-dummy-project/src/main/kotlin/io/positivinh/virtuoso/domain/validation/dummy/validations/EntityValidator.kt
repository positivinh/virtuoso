package io.positivinh.virtuoso.domain.validation.dummy.validations

import io.positivinh.virtuoso.domain.validation.dummy.model.EntityToValidate
import io.positivinh.virtuoso.domain.validations.AbstractEntityValidator
import io.positivinh.virtuoso.domain.validations.SimpleValidator
import org.springframework.stereotype.Component
import org.springframework.validation.Validator

@Component
class EntityValidator(validator: Validator) : AbstractEntityValidator<EntityToValidate>(validator) {

    override fun customValidators(): List<SimpleValidator<EntityToValidate>> {

        return listOf(
            DummyCustomerValidator()
        )
    }
}
