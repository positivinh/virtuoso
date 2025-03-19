package io.positivinh.virtuoso.authentication.jwt.dummy

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtDummyProjectApplicationTest {

    @Autowired
    private lateinit var jwtDummyProjectApplication: JwtDummyProjectApplication

    @Test
    fun contextLoads() {

        Assertions.assertThat(jwtDummyProjectApplication.jwtTokenDecoder).isNotNull
        Assertions.assertThat(jwtDummyProjectApplication.jwtTokenCreator).isNotNull
    }
}