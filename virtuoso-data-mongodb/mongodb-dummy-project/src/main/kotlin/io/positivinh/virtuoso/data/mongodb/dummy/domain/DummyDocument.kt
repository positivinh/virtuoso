package io.positivinh.virtuoso.data.mongodb.dummy.domain

import io.positivinh.virtuoso.data.mongodb.autoconfigure.mongodb.audit.AuditData
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.OffsetDateTime

@Document(collection = "dummy")
data class DummyDocument(

    @Id
    var id: String? = null,

    var name: String? = null,

    var offsetDateTime: OffsetDateTime? = OffsetDateTime.now(),

    var auditData: AuditData = AuditData()
)