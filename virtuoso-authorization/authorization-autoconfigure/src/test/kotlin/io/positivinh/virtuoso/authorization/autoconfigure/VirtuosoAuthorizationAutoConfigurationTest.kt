package io.positivinh.virtuoso.authorization.autoconfigure

import io.positivinh.virtuoso.authorization.core.Requester
import io.positivinh.virtuoso.authorization.core.RequesterProvider
import io.positivinh.virtuoso.authorization.spring.PolicyPermissionEvaluator
import io.positivinh.virtuoso.authorization.spring.SecurityContextRequesterProvider
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.core.Authentication
import java.io.Serializable

class VirtuosoAuthorizationAutoConfigurationTest {

    private val contextRunner = ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(VirtuosoAuthorizationAutoConfiguration::class.java))

    @Test
    fun defaultBeans() {

        contextRunner.run { context ->
            Assertions.assertThat(context).hasSingleBean(MethodSecurityExpressionHandler::class.java)
            Assertions.assertThat(context.getBean("appCustomPermissionEvaluator"))
                .isInstanceOf(PolicyPermissionEvaluator::class.java)
            Assertions.assertThat(context.getBean(RequesterProvider::class.java))
                .isInstanceOf(SecurityContextRequesterProvider::class.java)
        }
    }

    @Test
    fun customPermissionEvaluator_backsOff() {

        contextRunner
            .withBean(
                "appCustomPermissionEvaluator",
                PermissionEvaluator::class.java,
                { AllowAllPermissionEvaluator() })
            .run { context ->
                Assertions.assertThat(context.getBean("appCustomPermissionEvaluator"))
                    .isInstanceOf(AllowAllPermissionEvaluator::class.java)
                Assertions.assertThat(context.getBeansOfType(PolicyPermissionEvaluator::class.java)).isEmpty()
            }
    }

    @Test
    fun customRequesterProvider_backsOff() {

        val custom = object : RequesterProvider {
            override fun currentRequester() = Requester("system")
        }

        contextRunner
            .withBean(RequesterProvider::class.java, { custom })
            .run { context ->
                Assertions.assertThat(context).hasSingleBean(RequesterProvider::class.java)
                Assertions.assertThat(context.getBean(RequesterProvider::class.java)).isSameAs(custom)
            }
    }

    private class AllowAllPermissionEvaluator : PermissionEvaluator {

        override fun hasPermission(authentication: Authentication, targetDomainObject: Any?, permission: Any) = true

        override fun hasPermission(
            authentication: Authentication,
            targetId: Serializable,
            targetType: String,
            permission: Any
        ) = true
    }
}
