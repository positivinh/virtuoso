package io.positivinh.virtuoso.observability.observation

import io.micrometer.observation.Observation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.http.client.observation.ClientRequestObservationContext
import org.springframework.http.server.observation.ServerRequestObservationContext
import org.springframework.mock.http.client.MockClientHttpRequest
import org.springframework.mock.http.server.reactive.MockServerHttpRequest
import org.springframework.mock.http.server.reactive.MockServerHttpResponse
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import java.net.URI
import org.springframework.http.server.reactive.observation.ServerRequestObservationContext as ReactiveServerRequestObservationContext

class IgnoredPathsObservationPredicateTest {

    private val predicate = IgnoredPathsObservationPredicate(listOf("/actuator/**", "/eureka/**"))

    @Test
    fun servletRequest_ignoredPath_dropped() {

        Assertions.assertThat(predicate.test("http.server.requests", servletContext("/actuator/health"))).isFalse()
    }

    @Test
    fun servletRequest_otherPath_kept() {

        Assertions.assertThat(predicate.test("http.server.requests", servletContext("/api/users/vinh"))).isTrue()
    }

    @Test
    fun servletRequest_pathWithinContextPath() {

        val request = MockHttpServletRequest("GET", "/app/actuator/health").apply { contextPath = "/app" }
        val context = ServerRequestObservationContext(request, MockHttpServletResponse())

        Assertions.assertThat(predicate.test("http.server.requests", context)).isFalse()
    }

    @Test
    fun reactiveRequest_ignoredPath_dropped() {

        val context = ReactiveServerRequestObservationContext(
            MockServerHttpRequest.get("/eureka/apps/").build(),
            MockServerHttpResponse(),
            emptyMap(),
        )

        Assertions.assertThat(predicate.test("http.server.requests", context)).isFalse()
    }

    @Test
    fun reactiveRequest_otherPath_kept() {

        val context = ReactiveServerRequestObservationContext(
            MockServerHttpRequest.get("/api/authenticate").build(),
            MockServerHttpResponse(),
            emptyMap(),
        )

        Assertions.assertThat(predicate.test("http.server.requests", context)).isTrue()
    }

    @Test
    fun clientRequest_eurekaHeartbeat_dropped() {

        val context = ClientRequestObservationContext(
            MockClientHttpRequest(HttpMethod.PUT, URI.create("http://service-discovery:8761/eureka/apps/USER-SERVICE/x")),
        )

        Assertions.assertThat(predicate.test("http.client.requests", context)).isFalse()
    }

    @Test
    fun otherObservation_kept() {

        Assertions.assertThat(predicate.test("mongodb.command", Observation.Context())).isTrue()
    }

    private fun servletContext(path: String) =
        ServerRequestObservationContext(MockHttpServletRequest("GET", path), MockHttpServletResponse())
}
