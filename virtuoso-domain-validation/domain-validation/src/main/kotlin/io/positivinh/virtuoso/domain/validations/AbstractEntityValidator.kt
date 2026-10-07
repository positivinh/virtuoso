package io.positivinh.virtuoso.domain.validations

import com.crabshue.commons.kotlin.logging.getLogger
import io.positivinh.virtuoso.domain.validations.exceptions.EntityErrorType
import io.positivinh.virtuoso.domain.validations.exceptions.ValidationException
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import org.springframework.validation.Validator

abstract class AbstractEntityValidator<T : Any>(private val validator: Validator) : EntityValidator<T> {

    private val logger = getLogger()

    override fun validate(entity: T) {

        val errors = provideNewErrors(entity)

        validateEntity(entity, errors)

        this.customValidators()
            .forEach { validator -> validator.validate(entity, errors) }

        if (errors.hasErrors()) {
            // never log or attach the entity itself: it may hold secrets (e.g. a password) or personal data
            logger.error(
                "Entity [{}] is invalid {}",
                entity::class.java.simpleName,
                errors.allErrors.map { "${(it as? FieldError)?.field ?: it.objectName}: ${it.code}" })
            throw ValidationException(EntityErrorType.ENTITY_INVALID, errors)
        }

        logger.debug("Validation [{}] OK", entity::class.java.simpleName)
    }

    private fun validateEntity(entity: T, errors: Errors) {

        validator.validate(entity, errors)
    }

}
