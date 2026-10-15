package io.positivinh.virtuoso.domain.validation.dummy.model

import io.positivinh.virtuoso.domain.validation.dummy.model.test.EntityToValidateFixtureFactory
import io.positivinh.virtuoso.domain.validations.test.DomainObjectValidator
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.junit.jupiter.api.Test

class EntityToValidateTest {

    @Test
    fun entityIsValid() {

        val entity = EntityToValidateFixtureFactory.defaultFixture()
        DomainObjectValidator.validateValidObject(entity)
    }

    @Test
    fun fieldMustNotBeBlank() {

        val entity = EntityToValidateFixtureFactory.fixtureWithBlankProperty()

        DomainObjectValidator.assertConstraintViolation(entity, "notBlankString", NotBlank::class)
    }

    @Test
    fun collectionMustNotBeEmpty() {

        val entity = EntityToValidateFixtureFactory.fixtureWithEmptyCollection()

        DomainObjectValidator.assertConstraintViolation(entity, "notEmptyCollection", NotEmpty::class)
    }

}
