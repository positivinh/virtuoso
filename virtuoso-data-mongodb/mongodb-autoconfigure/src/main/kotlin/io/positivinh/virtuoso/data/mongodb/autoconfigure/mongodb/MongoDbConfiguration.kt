package io.positivinh.virtuoso.data.mongodb.autoconfigure.mongodb

import com.crabshue.commons.kotlin.logging.getLogger
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean


@Configuration
@EnableMongoRepositories("\${virtuoso.mongodb.repositories.base-package}")
class MongoDbConfiguration {

    private val log = getLogger()

    @PostConstruct
    fun log() {

        log.info("mongodb configuration has started")
    }

    @Bean("mongoValidatorFactory")
    fun validatorFactory(): LocalValidatorFactoryBean {

        return LocalValidatorFactoryBean()
    }

    @Bean
    @ConditionalOnMissingBean(ValidatingEntityCallback::class)
    fun validatingMongoEventListener(@Qualifier("mongoValidatorFactory") validatorFactory: LocalValidatorFactoryBean): ValidatingEntityCallback {

        return ValidatingEntityCallback(validatorFactory)
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