package com.store.auth.domain.service

interface AuthPasswordEncryptor {

    fun encrypt(password: String): String

    fun matches(password: String, encodedPassword: String): Boolean
}
