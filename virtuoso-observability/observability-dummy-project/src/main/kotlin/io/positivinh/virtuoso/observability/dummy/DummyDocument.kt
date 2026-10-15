package io.positivinh.virtuoso.observability.dummy

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "dummies")
data class DummyDocument(

    @Id
    val id: String? = null,

    val secret: String? = null,
)
