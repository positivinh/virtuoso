package io.positivinh.virtuoso.observability.autoconfigure.configuration

import ch.qos.logback.classic.LoggerContext
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender
import io.positivinh.virtuoso.observability.logging.OpenTelemetryLogbackInstaller
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Log export over OTLP. Only when a logs endpoint is configured: without one Boot has no exporter, and the appender
 * would only cost time on every log line.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(OpenTelemetryAppender::class, LoggerContext::class, OpenTelemetry::class)
@ConditionalOnProperty(name = ["management.opentelemetry.logging.export.otlp.endpoint"])
@ConditionalOnBooleanProperty(name = ["virtuoso.observability.logs.enabled"], matchIfMissing = true)
class LogExportConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun openTelemetryLogbackInstaller(openTelemetry: OpenTelemetry): OpenTelemetryLogbackInstaller =
        OpenTelemetryLogbackInstaller(openTelemetry)
}
