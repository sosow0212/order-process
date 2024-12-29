package com.store.auth.fixture

import com.store.auth.application.port.`in`.command.SignInCommand
import com.store.auth.application.port.`in`.command.SignUpCommand
import com.store.auth.domain.Auth

class AuthFixture {

    companion object {
        fun 인증_생성(): Auth {
            return Auth(
                username = "username",
                password = "password"
            )
        }

        fun 인증_생성_커맨드(): SignUpCommand {
            return SignUpCommand(
                username = "username",
                password = "password"
            )
        }

        fun 인증_로그인_커맨드(): SignInCommand {
            return SignInCommand(
                username = "username",
                password = "password"
            )
        }
    }
}
