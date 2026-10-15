package io.positivinh.virtuoso.observability.autoconfigure.configuration

import com.mongodb.MongoClientSettings
import com.mongodb.observability.ObservabilitySettings
import io.micrometer.observation.ObservationRegistry
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.mongodb.autoconfigure.MongoClientSettingsBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * MongoDB command spans from the driver's own Micrometer support (Boot configures MongoDB metrics, not tracing).
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MongoClientSettings::class, MongoClientSettingsBuilderCustomizer::class)
@ConditionalOnBooleanProperty(name = ["virtuoso.observability.mongodb.enabled"], matchIfMissing = true)
class MongoObservabilityConfiguration {

    /**
     * Named so that an application can replace it. The registry is looked up when the client is built rather than
     * required here, so the customizer does nothing in an application without one.
     */
    @Bean
    @ConditionalOnMissingBean(name = [CUSTOMIZER_BEAN_NAME])
    fun virtuosoMongoObservabilityCustomizer(
        observationRegistry: ObjectProvider<ObservationRegistry>,
        properties: ObservabilityConfigurationProperties,
    ): MongoClientSettingsBuilderCustomizer = MongoClientSettingsBuilderCustomizer { builder ->
        observationRegistry.ifAvailable { registry ->
            builder.observabilitySettings(
                ObservabilitySettings.micrometerBuilder()
                    .observationRegistry(registry)
                    .enableCommandPayloadTracing(properties.mongodb.commandPayload)
                    .build(),
            )
        }
    }

    companion object {

        const val CUSTOMIZER_BEAN_NAME = "virtuosoMongoObservabilityCustomizer"
    }
}
