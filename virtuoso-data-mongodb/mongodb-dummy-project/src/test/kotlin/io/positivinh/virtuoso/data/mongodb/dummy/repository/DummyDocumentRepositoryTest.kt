package io.positivinh.virtuoso.data.mongodb.dummy.repository

import io.positivinh.virtuoso.data.mongodb.dummy.domain.DummyDocument
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
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

    @Test
    @WithMockUser(username = "test", roles = ["USER"])
    fun auditing() {

        val now = OffsetDateTime.now()
        val res = repository.save(DummyDocument(name = "test", offsetDateTime = now))

        Assertions.assertNotNull(res)
        Assertions.assertEquals(now, res.offsetDateTime)
        Assertions.assertEquals("test", res.auditData.createdBy)
        Assertions.assertEquals("test", res.auditData.lastModifiedBy)
        Assertions.assertNotNull(res.auditData.createdDateTime)
        Assertions.assertNotNull(res.auditData.lastModifiedDateTime)

        val lastModifiedDate = res.auditData.lastModifiedDateTime


        val res2 = repository.save(res)

        Assertions.assertTrue(lastModifiedDate!!.isBefore(res2.auditData.lastModifiedDateTime))
    }
}