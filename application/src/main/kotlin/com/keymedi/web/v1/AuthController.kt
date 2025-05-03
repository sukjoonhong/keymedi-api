package com.keymedi.web.v1

import com.keymedi.domain.model.LoginRequest
import com.keymedi.security.JwtAuthenticationFilter
import com.keymedi.security.JwtAuthenticationFilter.Companion.KEYMEDI_CUSTOM_HEADER
import com.keymedi.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "인증 API")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    @Operation(summary = "로그인")
    fun login(
        @RequestBody request: LoginRequest,
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        return try {
            val accessToken = authService.login(request)
            response.setHeader(KEYMEDI_CUSTOM_HEADER, accessToken)
            val cookie = JwtAuthenticationFilter.genAuthCookie(accessToken)
            response.addCookie(cookie)
            ResponseEntity.ok().build()
        } catch (_: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}