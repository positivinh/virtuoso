package io.positivinh.virtuoso.authentication.jwt

import io.positivinh.virtuoso.authentication.jwt.configuration.JwtAlgorithmConfiguration
import io.positivinh.virtuoso.authentication.jwt.configuration.JwtConfigurationProperties
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(classes = [JwtConfigurationProperties::class, JwtAlgorithmConfiguration::class, JwtTokenCreator::class])
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