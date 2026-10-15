package io.positivinh.virtuoso.authentication.jwt.autoconfigure

import com.auth0.jwt.JWT
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtAlgorithmConfiguration
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtConfiguration
import io.positivinh.virtuoso.authentication.jwt.autoconfigure.configuration.JwtConfigurationProperties
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@AutoConfiguration
@ConditionalOnClass(JWT::class)
@ConfigurationPropertiesScan
@PropertySource(value = ["classpath:jwt.properties"])
@EnableConfigurationProperties(JwtConfigurationProperties::class)
@Import(JwtAlgorithmConfiguration::class, JwtConfiguration::class)
//@Import(JwtConfiguration::class)
class JwtAutoConfiguration