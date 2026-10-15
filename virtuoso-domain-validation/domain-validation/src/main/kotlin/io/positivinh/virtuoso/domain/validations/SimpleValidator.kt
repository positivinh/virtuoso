package io.positivinh.virtuoso.domain.validations

import org.springframework.validation.Errors

fun interface SimpleValidator<T> {

    fun validate(entity: T, errors: Errors)
}
