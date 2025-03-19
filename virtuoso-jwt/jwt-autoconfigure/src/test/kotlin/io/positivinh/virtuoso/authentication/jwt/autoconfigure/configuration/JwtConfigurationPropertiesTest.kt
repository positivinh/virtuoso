package io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    initializers = [ConfigDataApplicationContextInitializer::class]
)
@EnableConfigurationProperties(value = [JwtConfigurationProperties::class])
@TestPropertySource(
    properties = [
        "virtuoso.jwt.secret=my secret",
        "virtuoso.jwt.issuer=my issuer"
    ]
)
class JwtConfigurationPropertiesTest {

    @Autowired
    private lateinit var jwtConfigurationProperties: JwtConfigurationProperties

    @Test
    fun parseProperties() {

        Assertions.assertThat(jwtConfigurationProperties.secret).isEqualTo("my secret")
        Assertions.assertThat(jwtConfigurationProperties.issuer).isEqualTo("my issuer")
    }

}