package com.store.global.exceptions

import org.springframework.http.HttpStatusCode

interface CustomExceptionType {

    val subject: String
    val message: String
    val httpStatusCode: HttpStatusCode
}
