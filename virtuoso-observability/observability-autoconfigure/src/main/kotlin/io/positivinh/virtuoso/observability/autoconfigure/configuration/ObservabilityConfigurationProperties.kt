package io.positivinh.virtuoso.observability.autoconfigure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "virtuoso.observability")
class ObservabilityConfigurationProperties {

    /**
     * Request paths (Ant patterns) that produce no observation, as server or client requests: health checks and Eureka
     * registration and heartbeats would otherwise fill the traces.
     */
    var ignoredPaths: List<String> = listOf("/actuator/**", "/eureka/**")

    val mongodb = MongoDb()

    val logs = Logs()

    class Logs {

        /**
         * Whether log lines are exported over OTLP. Takes effect only when
         * `management.opentelemetry.logging.export.otlp.endpoint` is set.
         */
        var enabled: Boolean = true
    }

    class MongoDb {

        /** Whether MongoDB command spans are created. */
        var enabled: Boolean = true

        /** Whether spans carry the command text. Off by default: commands hold password hashes and personal data. */
        var commandPayload: Boolean = false
    }
}
