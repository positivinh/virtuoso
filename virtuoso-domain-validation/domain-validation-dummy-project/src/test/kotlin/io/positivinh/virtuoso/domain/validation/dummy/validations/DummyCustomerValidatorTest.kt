package io.positivinh.virtuoso.domain.validation.dummy.validations

import io.positivinh.virtuoso.domain.validation.dummy.model.test.EntityToValidateFixtureFactory
import io.positivinh.virtuoso.domain.validations.test.EntityErrorsProvider
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DummyCustomerValidatorTest {

    private val dummyCustomerValidator = DummyCustomerValidator()

    @Test
    fun entityIsValid() {

        val entity = EntityToValidateFixtureFactory.defaultFixture()

        val errors = EntityErrorsProvider.provideNewErrors(entity)

        dummyCustomerValidator.validate(entity, errors)

        Assertions.assertThat(errors.allErrors).isEmpty()
    }


    @Test
    fun entityIsNotValid() {

        val entity = EntityToValidateFixtureFactory.fixtureWithInvalidCustomProperty()

        val errors = EntityErrorsProvider.provideNewErrors(entity)

        dummyCustomerValidator.validate(entity, errors)

        Assertions.assertThat(errors.allErrors).hasSize(1)

        val error = errors.fieldErrors[0]

        Assertions.assertThat(error.field).isEqualTo("propertyValidatedByCustomValidator")
        Assertions.assertThat(error.code).isEqualTo("CustomValidation")
    }

}
