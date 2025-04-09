package io.positivinh.virtuoso.authentication.jwt.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.crabshue.commons.kotlin.logging.getLogger
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtConfigurationProperties
import java.time.OffsetDateTime

class JwtTokenCreator(
    private val jwtAlgorithm: Algorithm,
    private val jwtConfigurationProperties: JwtConfigurationProperties
) {

    private val log = getLogger()

    fun createToken(username: String, authorities: List<String>): String {

        val jwtBuilder = JWT.create()
            .withIssuer(jwtConfigurationProperties.issuer)
            .withClaim(JwtConstants.JWT_USERNAME_CLAIM_KEY, username)
            .withClaim(JwtConstants.JWT_AUTHORITIES_CLAIM_KEY, authorities)

        jwtConfigurationProperties.expiryDelayInSeconds
            ?.let {
                if (it <= 0) {
                    log.warn("Expiration delay is not a positive value [{}]. Will not set token expiration.", it)
                    return@let
                }
                jwtBuilder.withExpiresAt(OffsetDateTime.now().plusSeconds(it).toInstant())
            }

        val token = jwtBuilder
            .sign(jwtAlgorithm)

        return token
    }
}