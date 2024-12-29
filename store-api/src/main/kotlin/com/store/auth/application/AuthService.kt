package com.store.auth.application

import com.store.auth.application.port.`in`.AuthUseCase
import com.store.auth.application.port.`in`.command.SignInCommand
import com.store.auth.application.port.`in`.command.SignUpCommand
import com.store.auth.application.port.out.AuthRepositoryPort
import com.store.auth.application.port.out.TokenProviderPort
import com.store.auth.domain.Auth
import com.store.auth.domain.service.AuthPasswordEncryptor
import com.store.auth.exception.AuthExceptionType
import com.store.auth.exception.AuthExceptionType.PASSWORD_INVALID_EXCEPTION
import com.store.auth.exception.AuthExceptionType.USERNAME_ALREADY_EXISTS_EXCEPTION
import com.store.global.exceptions.CustomException
import com.store.global.util.throwWhen
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authRepositoryPort: AuthRepositoryPort,
    private val authPasswordEncryptor: AuthPasswordEncryptor,
    private val tokenProviderPort: TokenProviderPort
) : AuthUseCase {

    override fun signUp(command: SignUpCommand): String {
        throwWhen(authRepositoryPort.existsByUsername(command.username)) {
            CustomException(USERNAME_ALREADY_EXISTS_EXCEPTION)
        }

        val savedAuth = authRepositoryPort.save(
            Auth.signUpWithEncryption(
                username = command.username,
                password = command.password,
                authPasswordEncryptor = authPasswordEncryptor
            )
        )

        return tokenProviderPort.create(savedAuth.id)
    }

    override fun signIn(command: SignInCommand): String {
        val auth = authRepositoryPort.findByUsername(command.username)
            ?: throw CustomException(AuthExceptionType.AUTH_NOT_FOUND_EXCEPTION)

        require(auth.matches(command.password, authPasswordEncryptor)) { PASSWORD_INVALID_EXCEPTION }
        return tokenProviderPort.create(auth.id)
    }
}
