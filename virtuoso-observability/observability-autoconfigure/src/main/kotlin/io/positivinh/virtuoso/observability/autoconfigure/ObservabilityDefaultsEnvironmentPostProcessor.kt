package io.positivinh.virtuoso.observability.autoconfigure

import org.springframework.boot.EnvironmentPostProcessor
import org.springframework.boot.SpringApplication
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.ResourcePropertySource

/**
 * Adds `observability.properties` as the lowest-priority property source, so every application or deployment setting
 * overrides it, and Boot's auto-configurations see it from the start.
 */
class ObservabilityDefaultsEnvironmentPostProcessor : EnvironmentPostProcessor {

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {

        if (!environment.propertySources.contains(PROPERTY_SOURCE_NAME)) {
            environment.propertySources.addLast(
                ResourcePropertySource(PROPERTY_SOURCE_NAME, ClassPathResource("observability.properties")),
            )
        }
    }

    companion object {

        const val PROPERTY_SOURCE_NAME = "virtuosoObservabilityDefaults"
    }
}
