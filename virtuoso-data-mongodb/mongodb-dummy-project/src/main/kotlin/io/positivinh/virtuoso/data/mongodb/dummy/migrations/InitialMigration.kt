package io.positivinh.virtuoso.data.mongodb.dummy.migrations

import com.crabshue.commons.kotlin.logging.getLogger
import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import io.positivinh.virtuoso.data.mongodb.dummy.domain.DummyDocument
import io.positivinh.virtuoso.data.mongodb.dummy.repository.DummyDocumentRepository

@ChangeUnit(id = "initial-migration", order = "001", author = "dummy-app")
class InitialMigration(
    val dummyDocumentRepository: DummyDocumentRepository
) {

    private val log = getLogger()

    @Execution
    fun initialMigration() {

        val document = DummyDocument(name = "document from migration")
        dummyDocumentRepository.save(document)
            .also { log.info("Saved document [${it.id}] to database") }
    }

    @RollbackExecution
    fun rollbackMigration() {
        log.info("Rollback migration - nothing to do.")
    }
}