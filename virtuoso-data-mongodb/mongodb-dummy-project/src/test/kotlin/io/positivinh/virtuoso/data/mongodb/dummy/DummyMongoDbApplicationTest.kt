package io.positivinh.virtuoso.data.mongodb.dummy

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback

@SpringBootTest
class DummyMongoDbApplicationTest {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun contextLoads() {

        Assertions.assertThat(applicationContext.getBeansOfType(ValidatingEntityCallback::class.java)).hasSize(1)
    }

    @Test
    fun springBootDefaultValidatorIsNotOverridden() {

        Assertions.assertThat(applicationContext.containsBean("defaultValidator")).isTrue()
        Assertions.assertThat(applicationContext.containsBean("mongoValidatorFactory")).isFalse()
    }
}
