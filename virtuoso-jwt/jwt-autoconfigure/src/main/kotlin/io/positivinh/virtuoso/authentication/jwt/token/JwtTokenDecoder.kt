package io.positivinh.virtuoso.authentication.jwt.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
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

    fun extracAuthenticationFromToken(token: String): AuthenticationVo {

        val decodedJwt = this.decodeToken(token)

        val username = decodedJwt.claims.getValue(JwtConstants.JWT_USERNAME_CLAIM_KEY).asString()
        val authorities = decodedJwt.claims.getValue(JwtConstants.JWT_AUTHORITIES_CLAIM_KEY).asList(String::class.java)

        return AuthenticationVo(username, authorities)
    }
}