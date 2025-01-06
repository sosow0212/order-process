package com.helper

import com.store.auth.application.port.out.AuthRepositoryPort
import com.store.auth.application.port.out.TokenProviderPort
import com.store.auth.domain.Auth
import com.store.auth.domain.service.AuthPasswordEncryptor
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseIntegrationTest : IntegrationHelper() {

    @Autowired
    private lateinit var authRepository: AuthRepositoryPort

    @Autowired
    private lateinit var authPasswordEncryptor: AuthPasswordEncryptor

    @Autowired
    private lateinit var tokenProviderPort: TokenProviderPort

    protected lateinit var auth: Auth
    protected lateinit var token: String

    @BeforeEach
    override fun init() {
        super.init() //
        setupTestUser()
    }

    private fun setupTestUser() {
        auth = authRepository.save(
            Auth.signUpWithEncryption(
                username = "testuser",
                password = "password",
                authPasswordEncryptor = authPasswordEncryptor
            )
        )
        token = tokenProviderPort.create(auth.id)
        RestAssured.authentication = RestAssured.oauth2(token)
    }
}
