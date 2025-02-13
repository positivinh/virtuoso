package io.positivinh.virtuoso.domain.validation.dummy.model.test

import org.apache.commons.lang3.StringUtils

object EntityTestConstants {

    const val NOT_BLANK = "NOT BLANK"

    const val BLANK = StringUtils.EMPTY

    val NOT_EMPTY_LIST = listOf("NOT EMPTY_LIST")

    val EMPTY_LIST = listOf<String>()

    const val CUSTOM_VALIDATION_VALID_VALUE = "something that is valid"

    const val CUSTOM_VALIDATION_INVALID_VALUE = "something that is wrong"
}
