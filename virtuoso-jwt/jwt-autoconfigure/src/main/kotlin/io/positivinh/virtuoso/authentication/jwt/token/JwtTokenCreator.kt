package io.positivinh.virtuoso.authentication.jwt.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtConfigurationProperties

class JwtTokenCreator(
    private val jwtAlgorithm: Algorithm,
    private val jwtConfigurationProperties: JwtConfigurationProperties
) {

    fun createToken(username: String, authorities: List<String>): String {

        val token = JWT.create()
            .withIssuer(jwtConfigurationProperties.issuer)
            .withClaim(JwtConstants.JWT_USERNAME_CLAIM_KEY, username)
            .withClaim(JwtConstants.JWT_AUTHORITIES_CLAIM_KEY, authorities)
            .sign(jwtAlgorithm)

        return token
    }
}