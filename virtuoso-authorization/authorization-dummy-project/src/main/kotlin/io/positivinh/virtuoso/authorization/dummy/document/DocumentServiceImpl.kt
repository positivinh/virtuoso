package io.positivinh.virtuoso.authorization.dummy.document

import io.positivinh.virtuoso.authorization.core.RequesterProvider
import org.springframework.stereotype.Service

@Service
class DocumentServiceImpl(
    private val requesterProvider: RequesterProvider
) : DocumentService {

    override fun readDocument(owner: String): String = "document of $owner"

    override fun readUnknown(owner: String): String = "unknown of $owner"

    override fun whoAmI(): String? = requesterProvider.currentRequester()?.username
}
