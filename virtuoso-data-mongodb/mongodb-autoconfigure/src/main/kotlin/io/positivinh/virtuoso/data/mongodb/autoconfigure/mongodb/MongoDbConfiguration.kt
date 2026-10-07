package io.positivinh.virtuoso.data.mongodb.autoconfigure.mongodb

import com.crabshue.commons.kotlin.logging.getLogger
import jakarta.annotation.PostConstruct
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Configuration
@EnableMongoRepositories("\${virtuoso.mongodb.repositories.base-package}")
class MongoDbConfiguration {

    private val log = getLogger()

    @PostConstruct
    fun log() {

        log.info("mongodb configuration has started")
    }

    /**
     * Validates documents before they are saved.
     *
     * Uses the application's validator. No validator bean is declared here: a library-defined
     * `Validator` bean would make Spring Boot's default validator back off.
     */
    @Bean
    @ConditionalOnMissingBean(ValidatingEntityCallback::class)
    fun validatingMongoEventListener(validator: ObjectProvider<Validator>): ValidatingEntityCallback {

        return ValidatingEntityCallback(
            validator.getIfAvailable { Validation.buildDefaultValidatorFactory().validator }
        )
    }

    @Bean
    @ConditionalOnMissingBean(MongoCustomConversions::class)
    fun mongoCustomConversions(): MongoCustomConversions {

        return MongoCustomConversions(
            listOf(
                ZonedDateTimeReadConverter,
                ZonedDateTimeWriteConverter,
                OffsetDateTimeReadConverter,
                OffsetDateTimeWriteConverter
            )
        )
    }
}