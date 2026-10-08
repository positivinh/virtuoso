package io.positivinh.virtuoso.observability.observation

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationPredicate
import org.springframework.util.AntPathMatcher

/**
 * Drops the observation of any HTTP request, served or sent, whose path matches one of [ignoredPaths] (Ant patterns).
 * Observations that are not HTTP requests, or whose path is not known yet, are kept.
 */
class IgnoredPathsObservationPredicate(private val ignoredPaths: List<String>) : ObservationPredicate {

    private val matcher = AntPathMatcher()

    override fun test(name: String, context: Observation.Context): Boolean {

        val path = RequestPaths.of(context) ?: return true
        return ignoredPaths.none { matcher.match(it, path) }
    }
}
