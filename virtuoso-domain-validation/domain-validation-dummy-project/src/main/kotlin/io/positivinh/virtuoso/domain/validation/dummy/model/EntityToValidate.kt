package io.positivinh.virtuoso.domain.validation.dummy.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class EntityToValidate(

    @get:NotBlank
    val notBlankString: String?,

    @get:NotEmpty
    val notEmptyCollection: List<String?>?,

    @get:NotBlank
    val propertyValidatedByCustomValidator: String? = null,
)
