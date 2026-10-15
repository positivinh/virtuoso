package io.positivinh.virtuoso.authorization.dummy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummyAuthorizationApplication

fun main(args: Array<String>) {

    runApplication<DummyAuthorizationApplication>(*args)
}
