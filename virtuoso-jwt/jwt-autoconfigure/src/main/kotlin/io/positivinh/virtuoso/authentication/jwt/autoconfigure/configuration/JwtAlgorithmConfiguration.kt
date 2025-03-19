package io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration

import com.auth0.jwt.algorithms.Algorithm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtAlgorithmConfiguration(private val jwtConfigurationProperties: JwtConfigurationProperties) {

    @Bean(name = ["jwtAlgorithm"])
    fun jwtAlgorithm(): Algorithm {

        return Algorithm.HMAC256(jwtConfigurationProperties.secret)
    }
}