package io.positivinh.virtuoso.data.mongodb.autoconfigure.mongock

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@AutoConfiguration
@ConfigurationPropertiesScan
@PropertySource(value = ["classpath:mongock.properties"])
@Import(MongockConfiguration::class)
class MongockAutoConfiguration