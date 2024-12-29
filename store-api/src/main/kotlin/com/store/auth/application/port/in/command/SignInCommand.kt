package com.store.auth.application.port.`in`.command

import com.store.auth.adapter.`in`.request.SignInRequest

data class SignInCommand(
    val username: String,
    val password: String
) {

    companion object {
        fun from(request: SignInRequest): SignInCommand {
            return SignInCommand(
                request.username,
                request.password
            )
        }
    }
}
