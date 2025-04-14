package io.positivinh.virtuoso.data.mongodb.autoconfigure.mongodb

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableMongoAuditing
@ConditionalOnClass(SecurityContextHolder::class)
class MongoDbAuditingConfiguration {

    @Bean
    fun auditorProvider(): AuditorAware<String> {

        return object : AuditorAware<String> {

            override fun getCurrentAuditor(): Optional<String> {

                if (SecurityContextHolder.getContext()?.authentication is AnonymousAuthenticationToken) {
                    return Optional.empty()
                }

                SecurityContextHolder.getContext()?.authentication?.isAuthenticated?.let {
                    if (!it) {
                        return Optional.empty()
                    }
                }

                if (SecurityContextHolder.getContext()?.authentication?.principal !is org.springframework.security.core.userdetails.User?) {
                    return Optional.empty()
                }

                val requestUser =
                    SecurityContextHolder.getContext()?.authentication?.principal as org.springframework.security.core.userdetails.User?

                val username = requestUser?.username

                return Optional.ofNullable(username)
            }
        }

    }
}