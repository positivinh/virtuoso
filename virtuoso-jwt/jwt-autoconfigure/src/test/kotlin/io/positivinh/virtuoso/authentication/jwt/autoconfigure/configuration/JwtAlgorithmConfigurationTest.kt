package io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(classes = [JwtAlgorithmConfiguration::class, JwtConfigurationProperties::class])
@TestPropertySource(
    properties = [
        "virtuoso.jwt.secret=virtuoso-jwt-secret"
    ]
)
class JwtAlgorithmConfigurationTest {

    @Autowired
    private lateinit var jwtAlgorithmConfiguration: JwtAlgorithmConfiguration

    @Test
    fun createAlgorithmBean() {

        val res = jwtAlgorithmConfiguration.jwtAlgorithm()

        Assertions.assertThat(res).isNotNull
        Assertions.assertThat(res.name).isEqualTo("HS256")
    }

}