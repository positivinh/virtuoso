package io.positivinh.virtuoso.observability.autoconfigure

import io.micrometer.observation.ObservationRegistry
import io.positivinh.virtuoso.observability.autoconfigure.configuration.LogExportConfiguration
import io.positivinh.virtuoso.observability.autoconfigure.configuration.MongoObservabilityConfiguration
import io.positivinh.virtuoso.observability.autoconfigure.configuration.ObservabilityConfigurationProperties
import io.positivinh.virtuoso.observability.autoconfigure.configuration.ObservationFilterConfiguration
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Import

/**
 * Virtuoso defaults on top of Spring Boot's OpenTelemetry support: MongoDB command spans without their payload, no
 * observations for health checks or Eureka traffic, and log export when a logs endpoint is configured.
 *
 * The `management.*` defaults (sampling, exporters off) come from `observability.properties` through
 * [ObservabilityDefaultsEnvironmentPostProcessor], not `@PropertySource`: Boot's own auto-configurations read them, and
 * a property source added while this class is parsed could arrive after they were evaluated.
 */
@AutoConfiguration
@ConditionalOnClass(ObservationRegistry::class)
@EnableConfigurationProperties(ObservabilityConfigurationProperties::class)
@Import(ObservationFilterConfiguration::class, MongoObservabilityConfiguration::class, LogExportConfiguration::class)
class ObservabilityAutoConfiguration
