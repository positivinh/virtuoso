package io.positivinh.virtuoso.observability.observation

import io.micrometer.observation.Observation
import org.springframework.util.ClassUtils

/**
 * Reads the request path of an HTTP observation context. Servlet, reactive and client contexts come from optional
 * libraries, so each is read by its own object, loaded only when its classes are on the classpath: a WebFlux gateway has
 * no servlet API, and a servlet service has no reactive server.
 */
internal object RequestPaths {

    private val classLoader = RequestPaths::class.java.classLoader

    private val servlet = ClassUtils.isPresent(
        "org.springframework.http.server.observation.ServerRequestObservationContext",
        classLoader,
    ) && ClassUtils.isPresent("jakarta.servlet.http.HttpServletRequest", classLoader)

    private val reactive = ClassUtils.isPresent(
        "org.springframework.http.server.reactive.observation.ServerRequestObservationContext",
        classLoader,
    )

    private val client = ClassUtils.isPresent(
        "org.springframework.http.client.observation.ClientRequestObservationContext",
        classLoader,
    )

    fun of(context: Observation.Context): String? =
        (if (servlet) ServletRequestPath.of(context) else null)
            ?: (if (reactive) ReactiveRequestPath.of(context) else null)
            ?: (if (client) ClientRequestPath.of(context) else null)
}

private object ServletRequestPath {

    fun of(context: Observation.Context): String? =
        (context as? org.springframework.http.server.observation.ServerRequestObservationContext)
            ?.carrier
            ?.let { it.requestURI.removePrefix(it.contextPath) }
}

private object ReactiveRequestPath {

    fun of(context: Observation.Context): String? =
        (context as? org.springframework.http.server.reactive.observation.ServerRequestObservationContext)
            ?.carrier
            ?.path
            ?.pathWithinApplication()
            ?.value()
}

/** `RestClient` and `RestTemplate`. A `WebClient` request is not built yet when its observation starts. */
private object ClientRequestPath {

    fun of(context: Observation.Context): String? =
        (context as? org.springframework.http.client.observation.ClientRequestObservationContext)
            ?.carrier
            ?.uri
            ?.path
}
