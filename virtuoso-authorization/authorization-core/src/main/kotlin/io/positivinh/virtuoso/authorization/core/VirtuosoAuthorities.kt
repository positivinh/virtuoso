package io.positivinh.virtuoso.authorization.core

/**
 * Authorities used across Virtuoso services, with the Spring `ROLE_` prefix. The gateway forwards them as issued by
 * the user service.
 */
object VirtuosoAuthorities {

    const val ROLE_USER = "ROLE_USER"

    const val ROLE_ADMIN = "ROLE_ADMIN"

    const val ROLE_SYSTEM = "ROLE_SYSTEM"

    /**
     * Authorities that may perform any operation on any user's data.
     */
    val ADMINISTRATORS = setOf(ROLE_ADMIN, ROLE_SYSTEM)
}
