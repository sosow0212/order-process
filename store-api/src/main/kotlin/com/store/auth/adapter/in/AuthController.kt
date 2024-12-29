package com.store.auth.adapter.`in`

import com.store.auth.adapter.`in`.request.SignInRequest
import com.store.auth.adapter.`in`.request.SignUpRequest
import com.store.auth.adapter.`in`.response.AuthResponse
import com.store.auth.application.port.`in`.AuthUseCase
import com.store.auth.application.port.`in`.command.SignInCommand
import com.store.auth.application.port.`in`.command.SignUpCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val authUseCase: AuthUseCase,
) : AuthApi {

    @PostMapping("/sign-up")
    override fun signUp(request: SignUpRequest): ResponseEntity<AuthResponse> {
        val token = authUseCase.signUp(SignUpCommand.from(request))
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(AuthResponse(token))
    }

    @GetMapping("/sign-in")
    override fun signIn(request: SignInRequest): ResponseEntity<AuthResponse> {
        val token = authUseCase.signIn(SignInCommand.from(request))
        return ResponseEntity.ok(AuthResponse(token))
    }
}
