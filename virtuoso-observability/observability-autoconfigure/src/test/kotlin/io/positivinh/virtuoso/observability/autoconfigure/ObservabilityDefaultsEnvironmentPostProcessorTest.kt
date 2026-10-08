package io.positivinh.virtuoso.observability.autoconfigure

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringApplication
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.StandardEnvironment

class ObservabilityDefaultsEnvironmentPostProcessorTest {

    private val postProcessor = ObservabilityDefaultsEnvironmentPostProcessor()

    @Test
    fun safeDefaults() {

        val environment = StandardEnvironment()

        postProcessor.postProcessEnvironment(environment, SpringApplication())

        Assertions.assertThat(environment.getProperty("management.tracing.sampling.probability")).isEqualTo("0.1")
        Assertions.assertThat(environment.getProperty("management.otlp.metrics.export.enabled")).isEqualTo("false")
        Assertions.assertThat(environment.getProperty("management.metrics.distribution.slo.http.server.requests"))
            .startsWith("5ms,")
        // no endpoint: Boot creates no trace or log exporter without one
        Assertions.assertThat(environment.containsProperty("management.opentelemetry.tracing.export.otlp.endpoint"))
            .isFalse()
    }

    @Test
    fun applicationSettingsWin() {

        val environment = StandardEnvironment()
        environment.propertySources.addFirst(
            MapPropertySource("application", mapOf("management.tracing.sampling.probability" to "1.0")),
        )

        postProcessor.postProcessEnvironment(environment, SpringApplication())

        Assertions.assertThat(environment.getProperty("management.tracing.sampling.probability")).isEqualTo("1.0")
    }

    @Test
    fun addedOnce() {

        val environment = StandardEnvironment()

        postProcessor.postProcessEnvironment(environment, SpringApplication())
        postProcessor.postProcessEnvironment(environment, SpringApplication())

        Assertions.assertThat(environment.propertySources.count {
            it.name == ObservabilityDefaultsEnvironmentPostProcessor.PROPERTY_SOURCE_NAME
        }).isEqualTo(1)
    }
}
