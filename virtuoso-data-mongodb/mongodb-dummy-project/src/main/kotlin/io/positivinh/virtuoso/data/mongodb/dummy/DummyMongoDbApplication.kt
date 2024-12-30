package io.positivinh.virtuoso.data.mongodb.dummy

import io.positivinh.virtuoso.data.mongodb.autoconfigure.MongoDbAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(MongoDbAutoConfiguration::class)
class DummyMongoDbApplication {
}

fun main(args: Array<String>) {

    runApplication<DummyMongoDbApplication>(*args)
}