package io.positivinh.softwarefactory.data.mongodb.autoconfigure.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean


@Configuration
@EnableMongoRepositories("\${positivinh.mongodb.repositories.base-package}")
@EnableMongoAuditing
class MongoDbConfiguration {

    @Bean
    @ConditionalOnMissingBean(LocalValidatorFactoryBean::class)
    fun validator(): LocalValidatorFactoryBean = LocalValidatorFactoryBean()

    @Bean
    @ConditionalOnMissingBean(ValidatingMongoEventListener::class)
    fun validatingMongoEventListener(): ValidatingMongoEventListener = ValidatingMongoEventListener(validator())

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

//    @Bean
//    fun myAuditorProvider(): AuditorAware<AuditableUser> {
//        return AuditorAwareImpl()
//    }
}