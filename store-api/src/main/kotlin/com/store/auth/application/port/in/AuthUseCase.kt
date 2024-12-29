package com.store.auth.application.port.`in`

import com.store.auth.application.port.`in`.command.SignInCommand
import com.store.auth.application.port.`in`.command.SignUpCommand

interface AuthUseCase {

    fun signUp(command: SignUpCommand): String

    fun signIn(command: SignInCommand): String
}
