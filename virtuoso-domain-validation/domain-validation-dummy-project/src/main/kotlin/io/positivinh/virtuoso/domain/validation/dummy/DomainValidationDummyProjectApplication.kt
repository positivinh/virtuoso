package io.positivinh.virtuoso.domain.validation.dummy

import com.crabshue.commons.kotlin.logging.getLogger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DomainValidationDummyProjectApplication {

    private val log = getLogger()

    fun helloWorld() {

        log.info("Hello world!")
    }
}

fun main(args: Array<String>) {

    runApplication<DomainValidationDummyProjectApplication>(*args)
}
