package io.positivinh.virtuoso.dummy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummyProjectApplication

fun main(args: Array<String>) {

    runApplication<DummyProjectApplication>(*args)
}