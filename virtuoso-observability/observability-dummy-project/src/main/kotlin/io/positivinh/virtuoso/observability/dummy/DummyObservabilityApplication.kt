package io.positivinh.virtuoso.observability.dummy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummyObservabilityApplication

fun main(args: Array<String>) {
    runApplication<DummyObservabilityApplication>(*args)
}
