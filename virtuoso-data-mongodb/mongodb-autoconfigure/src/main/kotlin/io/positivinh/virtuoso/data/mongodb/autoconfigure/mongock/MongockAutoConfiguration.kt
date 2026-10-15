package io.positivinh.virtuoso.data.mongodb.autoconfigure.mongock

import io.mongock.runner.springboot.EnableMongock
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@AutoConfiguration
@ConfigurationPropertiesScan
@PropertySource(value = ["classpath:mongock.properties"])
@Import(MongockConfiguration::class)
@ConditionalOnClass(EnableMongock::class)
class MongockAutoConfiguration