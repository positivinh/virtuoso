package io.positivinh.virtuoso.data.mongodb.autoconfigure.mongodb

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@AutoConfiguration(before = [MongoAutoConfiguration::class])
//@ConfigurationPropertiesScan
@PropertySource(value = ["classpath:/mongodb.properties"])
@Import(MongoDbConfiguration::class)
class MongoDbAutoConfiguration