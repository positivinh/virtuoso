package io.positivinh.virtuoso.authentication.jwt

import com.auth0.jwt.JWT
import io.positivinh.virtuoso.authentication.jwt.configuration.JwtAlgorithmConfiguration
import io.positivinh.virtuoso.authentication.jwt.configuration.JwtConfigurationProperties
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@AutoConfiguration
@ConditionalOnClass(JWT::class)
@ConfigurationPropertiesScan
@PropertySource("classpath:jwt.properties")
@EnableConfigurationProperties(JwtConfigurationProperties::class)
@Import(JwtAlgorithmConfiguration::class)
class JwtAutoConfiguration