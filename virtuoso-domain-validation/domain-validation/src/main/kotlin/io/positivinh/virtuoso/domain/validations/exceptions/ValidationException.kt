package io.positivinh.virtuoso.domain.validations.exceptions

import com.crabshue.commons.exceptions.ApplicationException
import com.crabshue.commons.exceptions.context.ErrorType
import org.springframework.validation.Errors

/**
 * Exception raised on validation errors.
 */
class ValidationException : ApplicationException {

    lateinit var errors: Errors

    constructor(errorType: ErrorType?, errors: Errors) : super(errorType) {
        initialize(errors)
    }

    constructor(errorType: ErrorType?, message: String?, errors: Errors) : super(errorType, message) {
        initialize(errors)
    }

    constructor(errorType: ErrorType?, cause: Throwable?, errors: Errors) : super(errorType, cause) {
        initialize(errors)
    }

    constructor(errorType: ErrorType?, message: String?, cause: Throwable?, errors: Errors) : super(errorType, message, cause) {
        initialize(errors)
    }

    private fun initialize(errors: Errors) {
        this.errors = errors
        this.addContextValue(EntityErrorContext.ERRORS, errors.allErrors)
    }

}
