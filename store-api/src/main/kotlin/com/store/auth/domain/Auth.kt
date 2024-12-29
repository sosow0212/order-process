package com.store.auth.domain

import com.store.auth.domain.service.AuthPasswordEncryptor

class Auth(
    val id: Long = 0,
    val username: String,
    val password: String,
) {

    fun matches(password: String, authPasswordEncryptor: AuthPasswordEncryptor): Boolean {
        return authPasswordEncryptor.matches(password, this.password)
    }

    companion object {
        fun signUpWithEncryption(
            username: String,
            password: String,
            authPasswordEncryptor: AuthPasswordEncryptor
        ) = Auth(username = username, password = authPasswordEncryptor.encrypt(password))
    }
}
