package io.positivinh.virtuoso.domain.validations.test

import org.springframework.validation.DirectFieldBindingResult
import org.springframework.validation.Errors

object EntityErrorsProvider {

    fun <T> provideNewErrors(entity: T): Errors {

        return DirectFieldBindingResult(entity, entity!!::class.java.simpleName)
    }
}
