package io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration

import com.auth0.jwt.algorithms.Algorithm
import io.positivinh.virtuoso.authentication.jwt.token.JwtTokenCreator
import io.positivinh.virtuoso.authentication.jwt.token.JwtTokenDecoder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun jwtTokenCreator(
        @Qualifier("jwtAlgorithm") jwtAlgorithm: Algorithm,
        jwtConfigurationProperties: JwtConfigurationProperties
    ): JwtTokenCreator {

        return JwtTokenCreator(jwtAlgorithm, jwtConfigurationProperties)
    }

    @Bean
    @ConditionalOnMissingBean
    fun jwtTokenDecoder(
        @Qualifier("jwtAlgorithm") jwtAlgorithm: Algorithm,
        jwtConfigurationProperties: JwtConfigurationProperties
    ): JwtTokenDecoder {

        return JwtTokenDecoder(jwtAlgorithm, jwtConfigurationProperties)
    }
}