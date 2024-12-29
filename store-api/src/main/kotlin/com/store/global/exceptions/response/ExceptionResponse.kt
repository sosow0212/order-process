package com.store.global.exceptions.response

import org.springframework.http.HttpStatusCode

data class ExceptionResponse(
    val name: String,
    val customCode: HttpStatusCode,
    val message: String
)
