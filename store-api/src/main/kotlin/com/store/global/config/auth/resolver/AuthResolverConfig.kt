package com.store.global.config.auth.resolver

import com.store.global.config.auth.resolver.AuthMemberArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthResolverConfig(
    private val authMemberArgumentResolver: AuthMemberArgumentResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authMemberArgumentResolver)
    }
}
