package io.positivinh.virtuoso.authentication.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import io.positivinh.virtuoso.authentication.jwt.configuration.JwtConfigurationProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Component


@Component
@ConditionalOnBean(name = ["jwtAlgorithm"], value = [JwtConfigurationProperties::class])
class JwtTokenDecoder(
    @Qualifier("jwtAlgorithm") private val jwtAlgorithm: Algorithm,
    private val jwtConfigurationProperties: JwtConfigurationProperties
) {

    private val verifier: JWTVerifier = JWT.require(jwtAlgorithm) // specify any specific claim validations
        .withIssuer(jwtConfigurationProperties.issuer) // reusable verifier instance
        .build()

    fun decodeToken(token: String): DecodedJWT {

        val decodedJWT: DecodedJWT = verifier.verify(token)

        return decodedJWT
    }
}