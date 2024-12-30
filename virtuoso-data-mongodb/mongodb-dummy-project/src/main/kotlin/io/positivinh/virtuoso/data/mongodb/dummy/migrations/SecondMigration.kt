package io.positivinh.virtuoso.data.mongodb.dummy.migrations

import com.crabshue.commons.kotlin.logging.getLogger
import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import io.positivinh.virtuoso.data.mongodb.dummy.domain.DummyDocument
import org.springframework.data.mongodb.core.MongoTemplate

@ChangeUnit(id = "second-migration", order = "002", author = "dummy-app")
class SecondMigration(
    val mongoTemplate: MongoTemplate,
) {

    private val log = getLogger()

    @Execution
    fun secondMigration() {

        val document = DummyDocument(name = "second document from migration")
        mongoTemplate.save(document)
            .also { log.info("Saved second document [${it.id}] to database") }
    }

    @RollbackExecution
    fun rollbackMigration() {
        log.info("Rollback migration - nothing to do.")
    }
}