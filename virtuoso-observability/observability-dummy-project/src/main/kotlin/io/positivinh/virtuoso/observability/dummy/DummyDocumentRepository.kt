package io.positivinh.virtuoso.observability.dummy

import org.springframework.data.mongodb.repository.MongoRepository

interface DummyDocumentRepository : MongoRepository<DummyDocument, String> {

    fun findBySecret(secret: String): DummyDocument?
}
