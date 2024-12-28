package io.positivinh.softwarefactory.data.mongodb.dummy.repository

import io.positivinh.softwarefactory.data.mongodb.dummy.domain.DummyDocument
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime

@SpringBootTest
class DummyDocumentRepositoryTest {

    @Autowired
    private lateinit var repository: DummyDocumentRepository

    @Test
    fun crud() {

        val now = OffsetDateTime.now()
        val res = repository.save(DummyDocument(name = "test", offsetDateTime = now))

        Assertions.assertNotNull(res)
        Assertions.assertEquals(now, res.offsetDateTime)
    }
}