package io.positivinh.virtuoso.authentication.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.positivinh.virtuoso.authentication.jwt.configuration.JwtConfigurationProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Component

@Component
@ConditionalOnBean(name = ["jwtAlgorithm"], value = [JwtConfigurationProperties::class])
class JwtTokenCreator(
    @Qualifier("jwtAlgorithm") private val jwtAlgorithm: Algorithm,
    private val jwtConfigurationProperties: JwtConfigurationProperties
) {

    fun createToken(username: String, authorities: List<String>): String {

        val token = JWT.create()
            .withIssuer(jwtConfigurationProperties.issuer)
            .withClaim("username", username)
            .withClaim("authorities", authorities)
            .sign(jwtAlgorithm)

        return token
    }
}