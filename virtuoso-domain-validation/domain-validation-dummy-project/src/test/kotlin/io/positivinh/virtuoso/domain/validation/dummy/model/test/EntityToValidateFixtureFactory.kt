package io.positivinh.virtuoso.domain.validation.dummy.model.test

import io.positivinh.virtuoso.domain.validation.dummy.model.EntityToValidate

object EntityToValidateFixtureFactory {

    fun defaultFixture(): EntityToValidate {

        return EntityToValidate(
            notBlankString = EntityTestConstants.NOT_BLANK,
            notEmptyCollection = EntityTestConstants.NOT_EMPTY_LIST,
            propertyValidatedByCustomValidator = EntityTestConstants.CUSTOM_VALIDATION_VALID_VALUE
        )
    }

    fun fixtureWithBlankProperty(): EntityToValidate {

        return EntityToValidate(
            notBlankString = EntityTestConstants.BLANK,
            notEmptyCollection = EntityTestConstants.NOT_EMPTY_LIST,
            propertyValidatedByCustomValidator = EntityTestConstants.CUSTOM_VALIDATION_VALID_VALUE
        )
    }

    fun fixtureWithEmptyCollection(): EntityToValidate {

        return EntityToValidate(
            notBlankString = EntityTestConstants.NOT_BLANK,
            notEmptyCollection = EntityTestConstants.EMPTY_LIST,
            propertyValidatedByCustomValidator = EntityTestConstants.CUSTOM_VALIDATION_VALID_VALUE
        )
    }

    fun fixtureWithInvalidCustomProperty(): EntityToValidate {

        return EntityToValidate(
            notBlankString = EntityTestConstants.NOT_BLANK,
            notEmptyCollection = EntityTestConstants.NOT_EMPTY_LIST,
            propertyValidatedByCustomValidator = EntityTestConstants.CUSTOM_VALIDATION_INVALID_VALUE
        )
    }
}
