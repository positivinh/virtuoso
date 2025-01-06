package io.positivinh.virtuoso.data.mongodb.dummy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummyMongoDbApplication {
}

fun main(args: Array<String>) {

    runApplication<DummyMongoDbApplication>(*args)
}