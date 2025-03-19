package io.positivinh.virtuoso.authentication.jwt.autoconfigure.token

import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtAlgorithmConfiguration
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtConfiguration
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtConfigurationProperties
import io.positivinh.virtuoso.authentication.jwt.token.JwtTokenCreator
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(classes = [JwtConfigurationProperties::class, JwtAlgorithmConfiguration::class, JwtConfiguration::class])
class JwtTokenCreatorTest {

    @Autowired
    private lateinit var jwtTokenCreator: JwtTokenCreator

    @Test
    fun createToken() {

        val res = jwtTokenCreator.createToken("username", mutableListOf("ROLE_USER", "ROLE_ADMIN"))

        Assertions.assertThat(res).isNotNull

        println(res)
    }

}