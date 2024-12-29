package com.store.auth.application

import com.store.auth.application.port.out.AuthRepositoryPort
import com.store.auth.application.port.out.TokenProviderPort
import com.store.auth.domain.service.AuthPasswordEncryptor
import com.store.auth.exception.AuthExceptionType.AUTH_NOT_FOUND_EXCEPTION
import com.store.auth.exception.AuthExceptionType.PASSWORD_INVALID_EXCEPTION
import com.store.auth.exception.AuthExceptionType.USERNAME_ALREADY_EXISTS_EXCEPTION
import com.store.auth.fixture.AuthFixture.Companion.인증_로그인_커맨드
import com.store.auth.fixture.AuthFixture.Companion.인증_생성
import com.store.auth.fixture.AuthFixture.Companion.인증_생성_커맨드
import com.store.global.exceptions.CustomException
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThatThrownBy

class AuthServiceTest : BehaviorSpec({

    val authRepositoryPort: AuthRepositoryPort = mockk()
    val authPasswordEncryptor: AuthPasswordEncryptor = mockk()
    val tokenProviderPort: TokenProviderPort = mockk()

    val authService = AuthService(authRepositoryPort, authPasswordEncryptor, tokenProviderPort)

    Given("회원 가입을 할 때") {
        When("닉네임이 이미 존재하면") {
            every { authRepositoryPort.existsByUsername(any()) } returns true

            Then("예외를 발생시킨다") {
                assertThatThrownBy {
                    authService.signUp(인증_생성_커맨드())
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(USERNAME_ALREADY_EXISTS_EXCEPTION.message)
            }
        }

        When("닉네임이 존재하지 않으면") {
            every { authRepositoryPort.existsByUsername(any()) } returns false
            every { authPasswordEncryptor.encrypt(any()) } returns "password"
            every { authRepositoryPort.save(any()) } returns 인증_생성()
            every { tokenProviderPort.create(any()) } returns "token"

            val command = 인증_생성_커맨드()
            Then("정상 가입이 되고 토큰을 반환한다") {
                val response = authService.signUp(command)
                response shouldBe "token"
            }
        }
    }

    Given("로그인을 진행할 때") {
        When("아이디가 존재하지 않으면") {
            every { authRepositoryPort.findByUsername(any()) } returns null

            Then("예외를 발생시킨다") {
                assertThatThrownBy {
                    authService.signIn(인증_로그인_커맨드())
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(AUTH_NOT_FOUND_EXCEPTION.message)
            }
        }

        When("아이디가 존재하고") {
            val auth = 인증_생성()
            every { authRepositoryPort.findByUsername(any()) } returns auth

            Then("패스워드가 일치하지 않으면 예외를 발생시킨다") {
                every { authPasswordEncryptor.matches(any(), any()) } returns false
                assertThatThrownBy {
                    authService.signIn(인증_로그인_커맨드())
                }.isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContaining(PASSWORD_INVALID_EXCEPTION.name)
            }

            Then("패스워드가 일치한다면 토큰을 반환한다") {
                every { authPasswordEncryptor.matches(any(), any()) } returns true
                every { tokenProviderPort.create(any()) } returns "token"

                val response = authService.signIn(인증_로그인_커맨드())
                response shouldBe "token"
            }
        }
    }
})
