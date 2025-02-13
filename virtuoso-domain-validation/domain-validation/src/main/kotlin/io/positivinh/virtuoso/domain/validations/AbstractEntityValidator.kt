package io.positivinh.virtuoso.domain.validations

import com.crabshue.commons.kotlin.logging.getLogger
import io.positivinh.virtuoso.domain.validations.exceptions.EntityErrorContext
import io.positivinh.virtuoso.domain.validations.exceptions.EntityErrorType
import io.positivinh.virtuoso.domain.validations.exceptions.ValidationException
import org.springframework.validation.Errors
import org.springframework.validation.Validator

abstract class AbstractEntityValidator<T : Any>(private val validator: Validator) : EntityValidator<T> {

    private val logger = getLogger()

    override fun validate(entity: T) {

        val errors = provideNewErrors(entity)

        validateEntity(entity, errors)

        this.customValidators()
            .forEach { validator -> validator.validate(entity, errors) }

        if (errors.hasErrors()) {
            logger.error("Entity [$entity] is invalid [${errors.allErrors}]")
            throw ValidationException(EntityErrorType.ENTITY_INVALID, errors)
                .addContextValue(EntityErrorContext.ENTITY, entity)
        }

        logger.info("Validation [${entity::class.java.simpleName}] OK [$entity]")
    }

    private fun validateEntity(entity: T, errors: Errors) {

        validator.validate(entity, errors)
    }

}
