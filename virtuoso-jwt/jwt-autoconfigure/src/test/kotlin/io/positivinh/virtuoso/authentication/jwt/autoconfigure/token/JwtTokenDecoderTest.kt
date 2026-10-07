package io.positivinh.virtuoso.authentication.jwt.autoconfigure.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtAlgorithmConfiguration
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtConfiguration
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtConfigurationProperties
import io.positivinh.virtuoso.authentication.jwt.token.JwtTokenDecoder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(classes = [JwtConfigurationProperties::class, JwtAlgorithmConfiguration::class, JwtConfiguration::class])
class JwtTokenDecoderTest {

    @Autowired
    private lateinit var jwtTokenDecoder: JwtTokenDecoder

    @Autowired
    private lateinit var jwtAlgorithm: Algorithm

    @Autowired
    private lateinit var jwtConfigurationProperties: JwtConfigurationProperties

    @Test
    fun decode() {

        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXJ0dW9zbyIsInVzZXJuYW1lIjoidXNlcm5hbWUiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiLCJST0xFX0FETUlOIl19.s9saufmQVgQUfm1X71O-2wq3IxZT_LyzDIWmddlzaks"

        val res = jwtTokenDecoder.decodeToken(token)

        Assertions.assertThat(res.getClaim("username").asString()).isEqualTo("username")

        Assertions.assertThat(res.getClaim("authorities").asList<String>(String::class.java))
            .containsAll(listOf("ROLE_USER", "ROLE_ADMIN"))
    }

    @Test
    fun extractAuthentication() {

        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXJ0dW9zbyIsInVzZXJuYW1lIjoidXNlcm5hbWUiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiLCJST0xFX0FETUlOIl19.s9saufmQVgQUfm1X71O-2wq3IxZT_LyzDIWmddlzaks"

        val (username, authorities) = jwtTokenDecoder.extracAuthenticationFromToken(token)

        Assertions.assertThat(username).isEqualTo("username")
        Assertions.assertThat(authorities).containsAll(listOf("ROLE_USER", "ROLE_ADMIN"))
    }

    @Test
    fun extractAuthentication_missingClaims_verificationFailure() {

        val token = JWT.create()
            .withIssuer(jwtConfigurationProperties.issuer)
            .sign(jwtAlgorithm)

        val thrownException = Assertions.catchException { jwtTokenDecoder.extracAuthenticationFromToken(token) }

        Assertions.assertThat(thrownException).isInstanceOf(JWTVerificationException::class.java)
    }
}
