package com.store.auth.adapter.out.persistence.entity

import com.store.auth.domain.Auth
import org.springframework.stereotype.Component

@Component
class AuthPersistenceMapper {

    fun toEntity(auth: Auth): AuthJpaEntity {
        return AuthJpaEntity(
            id = auth.id,
            username = auth.username,
            password = auth.password,
        )
    }

    fun toDomain(entity: AuthJpaEntity): Auth {
        return Auth(
            id = entity.id,
            username = entity.username,
            password = entity.password,
        )
    }
}
