package io.positivinh.virtuoso.authentication.jwt.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "virtuoso.jwt")
class JwtConfigurationProperties {

    var secret: String = "virtuoso-jwt-secret"

    var issuer: String = "virtuoso"
}