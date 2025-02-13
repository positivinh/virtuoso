package io.positivinh.virtuoso.domain.validation.dummy.validations

import com.crabshue.commons.exceptions.context.CommonErrorContext
import io.positivinh.virtuoso.domain.validation.dummy.model.test.EntityTestConstants
import io.positivinh.virtuoso.domain.validation.dummy.model.test.EntityToValidateFixtureFactory
import io.positivinh.virtuoso.domain.validations.exceptions.EntityErrorContext
import io.positivinh.virtuoso.domain.validations.exceptions.EntityErrorType
import io.positivinh.virtuoso.domain.validations.exceptions.ValidationException
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.validation.FieldError

@SpringBootTest
class EntityValidatorTest {

    @Autowired
    private lateinit var entityValidator: EntityValidator

    @Test
    fun validate_entityValid() {

        val entityToValidate = EntityToValidateFixtureFactory.defaultFixture()

        Assertions.assertThatCode { entityValidator.validate(entityToValidate) }
            .doesNotThrowAnyException()
    }

    @Test
    fun validate_whenBlankProperty_propertyIsInvalid() {

        val entityToValidate = EntityToValidateFixtureFactory.fixtureWithBlankProperty()

        val throwable: ValidationException = Assertions.catchThrowable { entityValidator.validate(entityToValidate) } as ValidationException

        Assertions.assertThat(throwable)
            .isExactlyInstanceOf(ValidationException::class.java)
        Assertions.assertThat(throwable.getFirstContextValue(CommonErrorContext.EXCEPTION_MESSAGE.name))
            .isEqualTo(EntityErrorType.ENTITY_INVALID)

        val errors = throwable.getFirstContextValue(EntityErrorContext.ERRORS.name) as List<*>
        Assertions.assertThat(errors.size).isEqualTo(1)

        val violation = errors[0] as FieldError
        Assertions.assertThat(violation).isNotNull
        Assertions.assertThat(violation).isInstanceOf(FieldError::class.java)

        Assertions.assertThat(violation.field).isEqualTo("notBlankString")
        Assertions.assertThat(violation.rejectedValue).isEqualTo(EntityTestConstants.BLANK)
        Assertions.assertThat(violation.code).isEqualTo(NotBlank::class.simpleName)
    }

    @Test
    fun validate_whenEmptyList_propertyIsInvalid() {

        val entityToValidate = EntityToValidateFixtureFactory.fixtureWithEmptyCollection()

        val throwable: ValidationException = Assertions.catchThrowable { entityValidator.validate(entityToValidate) } as ValidationException

        Assertions.assertThat(throwable)
            .isExactlyInstanceOf(ValidationException::class.java)
        Assertions.assertThat(throwable.getFirstContextValue(CommonErrorContext.EXCEPTION_MESSAGE.name))
            .isEqualTo(EntityErrorType.ENTITY_INVALID)

        val errors = throwable.getFirstContextValue(EntityErrorContext.ERRORS.name) as List<*>
        Assertions.assertThat(errors.size).isEqualTo(1)

        val violation = errors[0] as FieldError
        Assertions.assertThat(violation).isNotNull
        Assertions.assertThat(violation).isInstanceOf(FieldError::class.java)

        Assertions.assertThat(violation.field).isEqualTo("notEmptyCollection")
        Assertions.assertThat(violation.rejectedValue).isEqualTo(EntityTestConstants.EMPTY_LIST)
        Assertions.assertThat(violation.code).isEqualTo(NotEmpty::class.simpleName)
    }

    @Test
    fun validate_customValidation() {

        val entityInCollection = EntityToValidateFixtureFactory.fixtureWithInvalidCustomProperty()

        val thrown = Assertions.catchThrowable { entityValidator.validate(entityInCollection) }

        Assertions.assertThat(thrown)
            .isNotNull
            .isExactlyInstanceOf(ValidationException::class.java)

        thrown as ValidationException

        Assertions.assertThat(thrown.getFirstContextValue(CommonErrorContext.EXCEPTION_MESSAGE.name))
            .isEqualTo(EntityErrorType.ENTITY_INVALID)

        val errors = thrown.getFirstContextValue(EntityErrorContext.ERRORS.name) as List<*>
        Assertions.assertThat(errors.size).isEqualTo(1)

        val violation = errors[0] as FieldError
        Assertions.assertThat(violation).isNotNull

        Assertions.assertThat(violation.field).isEqualTo("propertyValidatedByCustomValidator")
        Assertions.assertThat(violation.rejectedValue).isEqualTo(EntityTestConstants.CUSTOM_VALIDATION_INVALID_VALUE)
        Assertions.assertThat(violation.code).isEqualTo("CustomValidation")
    }

}
