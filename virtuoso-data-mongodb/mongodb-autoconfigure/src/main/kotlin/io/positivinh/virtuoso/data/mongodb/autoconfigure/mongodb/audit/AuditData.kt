package io.positivinh.virtuoso.data.mongodb.autoconfigure.mongodb.audit

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

data class AuditData(

    @CreatedBy
    var createdBy: String? = null,

    @CreatedDate
    var createdDateTime: LocalDateTime? = null,

    @LastModifiedBy
    var lastModifiedBy: String? = null,

    @LastModifiedDate
    var lastModifiedDateTime: LocalDateTime? = null,
)