package io.positivinh.virtuoso.observability.autoconfigure

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import io.micrometer.observation.ObservationRegistry
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.logs.export.SimpleLogRecordProcessor
import io.opentelemetry.sdk.testing.exporter.InMemoryLogRecordExporter
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.positivinh.virtuoso.observability.logging.OpenTelemetryLogbackInstaller
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.ApplicationContextRunner

class LogExportConfigurationTest {

    private val exporter = InMemoryLogRecordExporter.create()

    private val openTelemetry: OpenTelemetrySdk = OpenTelemetrySdk.builder()
        .setLoggerProvider(
            SdkLoggerProvider.builder().addLogRecordProcessor(SimpleLogRecordProcessor.create(exporter)).build(),
        )
        .setTracerProvider(SdkTracerProvider.builder().build())
        .build()

    private val contextRunner = ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(ObservabilityAutoConfiguration::class.java))
        .withBean(ObservationRegistry::class.java, { ObservationRegistry.create() })
        .withBean(OpenTelemetry::class.java, { openTelemetry })

    private val log = LoggerFactory.getLogger(LogExportConfigurationTest::class.java)

    @Test
    fun withEndpoint_logLinesExported() {

        contextRunner
            .withPropertyValues(ENDPOINT)
            .run { context ->
                Assertions.assertThat(context).hasSingleBean(OpenTelemetryLogbackInstaller::class.java)

                log.info("Created user [{}]", "someone")

                Assertions.assertThat(exporter.finishedLogRecordItems.map { it.bodyValue?.asString() })
                    .contains("Created user [someone]")
            }
    }

    @Test
    fun withEndpoint_logLineInsideSpan_carriesTraceId() {

        contextRunner
            .withPropertyValues(ENDPOINT)
            .run {
                val span = openTelemetry.getTracer("test").spanBuilder("request").startSpan()
                span.makeCurrent().use { log.info("inside a span") }
                span.end()

                val record = exporter.finishedLogRecordItems.first { it.bodyValue?.asString() == "inside a span" }
                Assertions.assertThat(record.spanContext.traceId).isEqualTo(span.spanContext.traceId)
            }
    }

    @Test
    fun withoutEndpoint_nothingAttached() {

        contextRunner.run { context ->
            Assertions.assertThat(context).doesNotHaveBean(OpenTelemetryLogbackInstaller::class.java)
            Assertions.assertThat(rootLogger().getAppender(OpenTelemetryLogbackInstaller.APPENDER_NAME)).isNull()
        }
    }

    @Test
    fun disabled_nothingAttached() {

        contextRunner
            .withPropertyValues(ENDPOINT, "virtuoso.observability.logs.enabled=false")
            .run { context -> Assertions.assertThat(context).doesNotHaveBean(OpenTelemetryLogbackInstaller::class.java) }
    }

    @Test
    fun contextClosed_appenderDetached() {

        contextRunner
            .withPropertyValues(ENDPOINT)
            .run {
                Assertions.assertThat(rootLogger().getAppender(OpenTelemetryLogbackInstaller.APPENDER_NAME)).isNotNull
            }

        Assertions.assertThat(rootLogger().getAppender(OpenTelemetryLogbackInstaller.APPENDER_NAME)).isNull()
    }

    private fun rootLogger(): Logger =
        (LoggerFactory.getILoggerFactory() as LoggerContext).getLogger(Logger.ROOT_LOGGER_NAME)

    companion object {

        private const val ENDPOINT = "management.opentelemetry.logging.export.otlp.endpoint=http://localhost:4318/v1/logs"
    }
}
