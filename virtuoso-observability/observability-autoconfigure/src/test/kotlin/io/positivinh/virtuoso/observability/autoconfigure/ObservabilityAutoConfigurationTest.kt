package io.positivinh.virtuoso.observability.autoconfigure

import com.mongodb.MongoClientSettings
import com.mongodb.observability.micrometer.MicrometerObservabilitySettings
import io.micrometer.observation.ObservationRegistry
import io.positivinh.virtuoso.observability.autoconfigure.configuration.MongoObservabilityConfiguration
import io.positivinh.virtuoso.observability.observation.IgnoredPathsObservationPredicate
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.mongodb.autoconfigure.MongoClientSettingsBuilderCustomizer
import org.springframework.boot.test.context.FilteredClassLoader
import org.springframework.boot.test.context.runner.ApplicationContextRunner

class ObservabilityAutoConfigurationTest {

    private val observationRegistry = ObservationRegistry.create()

    private val contextRunner = ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(ObservabilityAutoConfiguration::class.java))
        .withBean(ObservationRegistry::class.java, { observationRegistry })

    @Test
    fun ignoredPathsPredicate() {

        contextRunner.run { context ->
            Assertions.assertThat(context).hasSingleBean(IgnoredPathsObservationPredicate::class.java)
        }
    }

    @Test
    fun mongoCustomizer_commandPayloadOffByDefault() {

        contextRunner.run { context ->
            val settings = customizedSettings(context.getBean(MongoClientSettingsBuilderCustomizer::class.java))

            Assertions.assertThat(settings.observationRegistry).isSameAs(observationRegistry)
            Assertions.assertThat(settings.isEnableCommandPayloadTracing).isFalse()
        }
    }

    @Test
    fun mongoCustomizer_commandPayloadWhenEnabled() {

        contextRunner
            .withPropertyValues("virtuoso.observability.mongodb.command-payload=true")
            .run { context ->
                val settings = customizedSettings(context.getBean(MongoClientSettingsBuilderCustomizer::class.java))

                Assertions.assertThat(settings.isEnableCommandPayloadTracing).isTrue()
            }
    }

    @Test
    fun mongoCustomizer_whenDisabled_absent() {

        contextRunner
            .withPropertyValues("virtuoso.observability.mongodb.enabled=false")
            .run { context ->
                Assertions.assertThat(context).doesNotHaveBean(MongoObservabilityConfiguration.CUSTOMIZER_BEAN_NAME)
            }
    }

    @Test
    fun mongoCustomizer_withoutMongoDriver_absent() {

        contextRunner
            .withClassLoader(FilteredClassLoader(MongoClientSettings::class.java))
            .run { context ->
                Assertions.assertThat(context).hasNotFailed()
                Assertions.assertThat(context).doesNotHaveBean(MongoObservabilityConfiguration.CUSTOMIZER_BEAN_NAME)
            }
    }

    @Test
    fun mongoCustomizer_backsOffForApplicationBean() {

        val applicationCustomizer = MongoClientSettingsBuilderCustomizer { }

        contextRunner
            .withBean(
                MongoObservabilityConfiguration.CUSTOMIZER_BEAN_NAME,
                MongoClientSettingsBuilderCustomizer::class.java,
                { applicationCustomizer },
            )
            .run { context ->
                Assertions.assertThat(context.getBean(MongoObservabilityConfiguration.CUSTOMIZER_BEAN_NAME))
                    .isSameAs(applicationCustomizer)
            }
    }

    @Test
    fun withoutObservationRegistryClass_backsOff() {

        ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ObservabilityAutoConfiguration::class.java))
            .withClassLoader(FilteredClassLoader(ObservationRegistry::class.java))
            .run { context ->
                Assertions.assertThat(context).hasNotFailed()
                Assertions.assertThat(context).doesNotHaveBean(IgnoredPathsObservationPredicate::class.java)
            }
    }

    private fun customizedSettings(customizer: MongoClientSettingsBuilderCustomizer): MicrometerObservabilitySettings {

        val builder = MongoClientSettings.builder()
        customizer.customize(builder)
        return builder.build().observabilitySettings as MicrometerObservabilitySettings
    }
}
