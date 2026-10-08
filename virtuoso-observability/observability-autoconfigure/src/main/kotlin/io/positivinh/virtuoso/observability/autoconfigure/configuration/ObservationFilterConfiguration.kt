package io.positivinh.virtuoso.observability.autoconfigure.configuration

import io.positivinh.virtuoso.observability.observation.IgnoredPathsObservationPredicate
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class ObservationFilterConfiguration {

    /** Boot applies every `ObservationPredicate` bean to the observation registry. */
    @Bean
    @ConditionalOnMissingBean
    fun ignoredPathsObservationPredicate(properties: ObservabilityConfigurationProperties): IgnoredPathsObservationPredicate =
        IgnoredPathsObservationPredicate(properties.ignoredPaths)
}
