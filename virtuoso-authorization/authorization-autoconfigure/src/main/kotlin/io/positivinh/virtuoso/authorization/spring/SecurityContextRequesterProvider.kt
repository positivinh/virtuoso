package io.positivinh.virtuoso.authorization.spring

import io.positivinh.virtuoso.authorization.core.Requester
import io.positivinh.virtuoso.authorization.core.RequesterProvider
import org.springframework.security.core.context.SecurityContextHolder

/**
 * [RequesterProvider] backed by Spring Security's [SecurityContextHolder].
 */
class SecurityContextRequesterProvider : RequesterProvider {

    override fun currentRequester(): Requester? {

        return SecurityContextHolder.getContext().authentication?.toRequester()
    }
}
