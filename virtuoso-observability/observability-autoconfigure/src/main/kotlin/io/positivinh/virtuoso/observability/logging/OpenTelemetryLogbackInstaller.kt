package io.positivinh.virtuoso.observability.logging

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean

/**
 * Attaches an [OpenTelemetryAppender] to Logback's root logger, so every log line is also sent to the OpenTelemetry log
 * pipeline that Spring Boot exports over OTLP. Done in code, so an application needs no `logback-spring.xml`.
 *
 * The appender's defaults are kept: a record carries the formatted message, level, logger and the active trace and span
 * ids, not the raw arguments or the MDC. Detached when the context closes, so test contexts don't stack appenders.
 */
class OpenTelemetryLogbackInstaller(private val openTelemetry: OpenTelemetry) : InitializingBean, DisposableBean {

    private var appender: OpenTelemetryAppender? = null

    override fun afterPropertiesSet() {

        val loggerContext = LoggerFactory.getILoggerFactory() as? LoggerContext ?: return
        val rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME)

        rootLogger.getAppender(APPENDER_NAME)?.let {
            rootLogger.detachAppender(it)
            it.stop()
        }
        appender = OpenTelemetryAppender()
            .apply {
                context = loggerContext
                name = APPENDER_NAME
                setOpenTelemetry(openTelemetry)
                start()
            }
            .also { rootLogger.addAppender(it) }
    }

    override fun destroy() {

        val loggerContext = LoggerFactory.getILoggerFactory() as? LoggerContext ?: return
        appender?.let {
            loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).detachAppender(it)
            it.stop()
        }
        appender = null
    }

    companion object {

        const val APPENDER_NAME = "VIRTUOSO_OPENTELEMETRY"
    }
}
