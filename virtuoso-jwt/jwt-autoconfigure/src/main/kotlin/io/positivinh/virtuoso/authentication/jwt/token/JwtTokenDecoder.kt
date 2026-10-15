package io.positivinh.virtuoso.authentication.jwt.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.MissingClaimException
import com.auth0.jwt.interfaces.DecodedJWT
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtConfigurationProperties
import io.positivinh.virtuoso.authentication.jwt.token.vo.AuthenticationVo


class JwtTokenDecoder(
    jwtAlgorithm: Algorithm,
    jwtConfigurationProperties: JwtConfigurationProperties
) {

    private val verifier: JWTVerifier = JWT.require(jwtAlgorithm) // specify any specific claim validations
        .withIssuer(jwtConfigurationProperties.issuer) // reusable verifier instance
        .build()

    fun decodeToken(token: String): DecodedJWT {

        val decodedJWT: DecodedJWT = verifier.verify(token)

        return decodedJWT
    }

    /**
     * Verifies [token] and returns the username and authorities it carries. A missing claim is a verification failure.
     */
    fun extractAuthenticationFromToken(token: String): AuthenticationVo {

        val decodedJwt = this.decodeToken(token)

        // missing or mistyped claims are verification failures (callers answer 401), not runtime errors
        val username = decodedJwt.getClaim(JwtConstants.JWT_USERNAME_CLAIM_KEY).asString()
            ?: throw MissingClaimException(JwtConstants.JWT_USERNAME_CLAIM_KEY)
        val authorities = decodedJwt.getClaim(JwtConstants.JWT_AUTHORITIES_CLAIM_KEY).asList(String::class.java)
            ?: throw MissingClaimException(JwtConstants.JWT_AUTHORITIES_CLAIM_KEY)

        return AuthenticationVo(username, authorities.toMutableList())
    }
}
