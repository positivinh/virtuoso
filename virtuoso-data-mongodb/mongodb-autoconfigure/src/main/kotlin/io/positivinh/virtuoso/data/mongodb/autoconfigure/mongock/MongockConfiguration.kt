package io.positivinh.virtuoso.data.mongodb.autoconfigure.mongock

import io.mongock.driver.mongodb.springdata.v4.SpringDataMongoV4Driver
import io.mongock.runner.springboot.EnableMongock
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
@EnableMongock
class MongockConfiguration {

    @PostConstruct
    fun postConstruct() {

        println("postconstruct mongockconfiguration")
    }

    @Bean
    fun springDataMongoV4Driver(mongoTemplate: MongoTemplate): SpringDataMongoV4Driver {

        return SpringDataMongoV4Driver.withDefaultLock(mongoTemplate)
    }

}

