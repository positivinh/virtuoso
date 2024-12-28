package io.positivinh.softwarefactory.data.mongodb.autoconfigure

import io.positivinh.softwarefactory.data.mongodb.autoconfigure.configuration.MongoDbConfiguration
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@AutoConfiguration
@ConfigurationPropertiesScan
@PropertySource(value = ["classpath:/mongodb.properties"])
@Import(MongoDbConfiguration::class)
class MongoDbAutoConfiguration