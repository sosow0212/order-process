package com.store.auth.adapter.`in`

import com.helper.IntegrationHelper
import com.store.auth.adapter.`in`.request.SignInRequest
import com.store.auth.adapter.`in`.request.SignUpRequest
import com.store.auth.adapter.out.persistence.AuthRepositoryAdapter
import com.store.auth.domain.Auth
import com.store.auth.domain.service.AuthPasswordEncryptor
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthControllerAcceptanceTest(
    @Autowired
    private val authRepositoryAdapter: AuthRepositoryAdapter,

    @Autowired
    private val authPasswordEncryptor: AuthPasswordEncryptor
) : IntegrationHelper() {

    @Test
    fun `회원가입을 진행한다`() {
        // given
        val request = SignUpRequest("username", "password")

        // when
        val response = RestAssured.given().log().all()
            .`when`()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/auth/sign-up")
            .then().log().all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.CREATED.value())
    }

    @Test
    fun `로그인을 진행한다`() {
        // given
        authRepositoryAdapter.save(
            Auth.signUpWithEncryption(
                username = "username",
                password = "password",
                authPasswordEncryptor = authPasswordEncryptor
            )
        )
        val request = SignInRequest("username", "password")

        // when
        val response = RestAssured.given().log().all()
            .`when`()
            .contentType(ContentType.JSON)
            .body(request)
            .get("/auth/sign-in")
            .then().log().all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }
}
