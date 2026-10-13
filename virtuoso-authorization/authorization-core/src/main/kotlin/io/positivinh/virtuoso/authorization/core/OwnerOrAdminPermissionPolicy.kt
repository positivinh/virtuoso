package io.positivinh.virtuoso.authorization.core

/**
 * A [PermissionPolicy] for one target type whose identifier is the owner's username: administrators may perform any
 * operation, the owner may perform [ownerOperations], everyone else is denied.
 *
 * Domain modules extend it with their own constants, e.g.
 * `class UserProfilePermissionPolicy : OwnerOrAdminPermissionPolicy(USER_PROFILE, setOf(READ, UPDATE))`.
 * Rules of another shape implement [PermissionPolicy] directly.
 *
 * @param targetType the only target type this policy supports
 * @param ownerOperations the operations the owner of a target may perform
 * @param administrators the authorities that may perform any operation, [VirtuosoAuthorities.ADMINISTRATORS] by default
 */
abstract class OwnerOrAdminPermissionPolicy(

    private val targetType: String,

    private val ownerOperations: Set<String>,

    private val administrators: Set<String> = VirtuosoAuthorities.ADMINISTRATORS,
) : PermissionPolicy {

    override fun supports(targetType: String): Boolean = targetType == this.targetType

    /**
     * Grants administrators any operation, and the owner (username equal to [targetId], ignoring case) the
     * [ownerOperations]. Other target types, other users and unknown operations are denied.
     */
    override fun isGranted(requester: Requester, targetType: String, targetId: String, operation: String): Boolean {

        if (!supports(targetType)) {
            return false
        }
        if (isAdministrator(requester)) {
            return true
        }
        return operation in ownerOperations && requester.username.equals(targetId, ignoreCase = true)
    }

    /**
     * Whether [requester] holds one of the [administrators] authorities.
     */
    protected fun isAdministrator(requester: Requester): Boolean =
        requester.authorities.any { it in administrators }
}
