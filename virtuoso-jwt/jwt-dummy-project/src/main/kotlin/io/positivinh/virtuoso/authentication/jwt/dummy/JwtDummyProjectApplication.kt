package io.positivinh.virtuoso.authentication.jwt.dummy

import io.positivinh.virtuoso.authentication.jwt.token.JwtTokenCreator
import io.positivinh.virtuoso.authentication.jwt.token.JwtTokenDecoder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication
class JwtDummyProjectApplication(
    val jwtTokenDecoder: JwtTokenDecoder,
    val jwtTokenCreator: JwtTokenCreator,
) {

    @EventListener(ApplicationReadyEvent::class)
    fun createToken() {

        val token = jwtTokenCreator.createToken("some user", listOf("ROLE_USER"))
        println(token)

        val decodedToken = jwtTokenDecoder.decodeToken(token)
        println(decodedToken.claims.getValue("username"))
        println(decodedToken.claims.getValue("authorities"))
    }
}

fun main(args: Array<String>) {
    runApplication<JwtDummyProjectApplication>(*args)
}