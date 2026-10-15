package io.positivinh.virtuoso.observability.dummy

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationHandler
import io.micrometer.tracing.Tracer
import io.opentelemetry.sdk.trace.export.SpanExporter
import io.positivinh.virtuoso.observability.observation.IgnoredPathsObservationPredicate
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.http.server.observation.ServerRequestObservationContext
import org.springframework.web.client.RestClient
import java.util.concurrent.ConcurrentLinkedQueue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DummyObservabilityApplicationTest {

    @TestConfiguration
    class RecordingObservations {

        /** Boot adds every `ObservationHandler` bean to its registry, after the Virtuoso predicate has filtered. */
        @Bean
        fun recordingObservationHandler() = RecordingObservationHandler()
    }

    class RecordingObservationHandler : ObservationHandler<Observation.Context> {

        val contexts = ConcurrentLinkedQueue<Observation.Context>()

        /** Read on stop: the servlet container recycles the request once the response is sent. */
        val serverRequestPaths = ConcurrentLinkedQueue<String>()

        override fun supportsContext(context: Observation.Context) = true

        override fun onStop(context: Observation.Context) {
            contexts.add(context)
            (context as? ServerRequestObservationContext)?.carrier?.let { serverRequestPaths.add(it.requestURI) }
        }
    }

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var observations: RecordingObservationHandler

    @Autowired
    private lateinit var dummyDocumentRepository: DummyDocumentRepository

    @LocalServerPort
    private var port: Int = 0

    @BeforeEach
    fun clear() {

        dummyDocumentRepository.deleteAll()
        observations.contexts.clear()
        observations.serverRequestPaths.clear()
    }

    @Test
    fun wiring() {

        Assertions.assertThat(applicationContext.getBean(Tracer::class.java)).isNotNull
        Assertions.assertThat(applicationContext.getBean(IgnoredPathsObservationPredicate::class.java)).isNotNull
        // no endpoint configured: nothing is exported
        Assertions.assertThat(applicationContext.getBeansOfType(SpanExporter::class.java)).isEmpty()
        Assertions.assertThat(applicationContext.environment.getProperty("management.otlp.metrics.export.enabled"))
            .isEqualTo("false")
    }

    @Test
    fun mongoCommand_traced_withoutCommandText() {

        dummyDocumentRepository.save(DummyDocument(secret = SECRET))
        Assertions.assertThat(dummyDocumentRepository.findBySecret(SECRET)).isNotNull

        val mongoContexts = observations.contexts.filter { it.name == "mongodb.command" }

        Assertions.assertThat(mongoContexts).isNotEmpty
        Assertions.assertThat(mongoContexts.flatMap { it.allKeyValues }.map { it.key })
            .doesNotContain("db.query.text")
        Assertions.assertThat(observations.contexts.flatMap { it.allKeyValues }.map { it.value })
            .noneMatch { it.contains(SECRET) }
    }

    @Test
    fun httpRequests_actuatorIgnored() {

        val client = RestClient.create("http://localhost:$port")
        client.get().uri("/hello").retrieve().toBodilessEntity()
        client.get().uri("/actuator/health").retrieve().toBodilessEntity()

        val observedPaths = waitForServerObservations(minimum = 1)

        Assertions.assertThat(observedPaths).contains("/hello")
        Assertions.assertThat(observedPaths).noneMatch { it.startsWith("/actuator") }
    }

    /** A server observation stops after the response is written, so it can land just after the client returns. */
    private fun waitForServerObservations(minimum: Int): List<String> {

        repeat(50) {
            val paths = serverRequestPaths()
            if (paths.size >= minimum) return paths
            Thread.sleep(100)
        }
        return serverRequestPaths()
    }

    private fun serverRequestPaths(): List<String> = observations.serverRequestPaths.toList()

    companion object {

        private const val SECRET = "s3cr3t-value"
    }
}
