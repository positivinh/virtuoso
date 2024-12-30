package io.positivinh.virtuoso.data.mongodb.dummy.repository

import io.positivinh.virtuoso.data.mongodb.dummy.domain.DummyDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DummyDocumentRepository : MongoRepository<DummyDocument, String>