package io.positivinh.virtuoso.authorization.core

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class OwnerOrAdminPermissionPolicyTest {

    private val policy = DocumentPolicy()

    @Test
    fun supports() {

        Assertions.assertThat(policy.supports(TARGET_TYPE)).isTrue()
        Assertions.assertThat(policy.supports("OTHER")).isFalse()
    }

    @Test
    fun owner() {

        val requester = Requester(OWNER, setOf(VirtuosoAuthorities.ROLE_USER))

        Assertions.assertThat(isGranted(requester, READ)).isTrue()
        Assertions.assertThat(isGranted(requester, UPDATE)).isTrue()
    }

    @Test
    fun owner_ignoresCase() {

        Assertions.assertThat(isGranted(Requester(OWNER.uppercase()), UPDATE)).isTrue()
    }

    @Test
    fun owner_whenOperationNotGranted_reject() {

        Assertions.assertThat(isGranted(Requester(OWNER), DELETE)).isFalse()
    }

    @Test
    fun whenOtherUser_reject() {

        val requester = Requester("other", setOf(VirtuosoAuthorities.ROLE_USER))

        Assertions.assertThat(isGranted(requester, READ)).isFalse()
        Assertions.assertThat(isGranted(requester, UPDATE)).isFalse()
    }

    @Test
    fun administrators() {

        val admin = Requester("admin", setOf(VirtuosoAuthorities.ROLE_ADMIN))
        val system = Requester("system", setOf(VirtuosoAuthorities.ROLE_SYSTEM))

        Assertions.assertThat(isGranted(admin, DELETE)).isTrue()
        Assertions.assertThat(isGranted(system, "ANY")).isTrue()
    }

    @Test
    fun customAdministrators() {

        val policy = object : OwnerOrAdminPermissionPolicy(TARGET_TYPE, setOf(READ), setOf("ROLE_AUDITOR")) {}

        val auditor = Requester("auditor", setOf("ROLE_AUDITOR"))
        val admin = Requester("admin", setOf(VirtuosoAuthorities.ROLE_ADMIN))

        Assertions.assertThat(policy.isGranted(auditor, TARGET_TYPE, OWNER, UPDATE)).isTrue()
        Assertions.assertThat(policy.isGranted(admin, TARGET_TYPE, OWNER, UPDATE)).isFalse()
    }

    @Test
    fun unknownTarget_reject() {

        val admin = Requester("admin", setOf(VirtuosoAuthorities.ROLE_ADMIN))

        Assertions.assertThat(policy.isGranted(Requester(OWNER), "OTHER", OWNER, READ)).isFalse()
        Assertions.assertThat(policy.isGranted(admin, "OTHER", OWNER, READ)).isFalse()
    }

    @Test
    fun isAdministrator() {

        Assertions.assertThat(policy.exposedIsAdministrator(Requester("admin", setOf(VirtuosoAuthorities.ROLE_ADMIN))))
            .isTrue()
        Assertions.assertThat(policy.exposedIsAdministrator(Requester(OWNER, setOf(VirtuosoAuthorities.ROLE_USER))))
            .isFalse()
    }

    private fun isGranted(requester: Requester, operation: String) =
        policy.isGranted(requester, TARGET_TYPE, OWNER, operation)

    private class DocumentPolicy : OwnerOrAdminPermissionPolicy(TARGET_TYPE, setOf(READ, UPDATE)) {

        fun exposedIsAdministrator(requester: Requester) = isAdministrator(requester)
    }

    companion object {

        private const val TARGET_TYPE = "DOCUMENT"
        private const val OWNER = "owner"
        private const val READ = "READ"
        private const val UPDATE = "UPDATE"
        private const val DELETE = "DELETE"
    }
}
