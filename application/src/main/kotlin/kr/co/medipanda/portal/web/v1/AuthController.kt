package kr.co.medipanda.portal.web.v1

import kr.co.medipanda.portal.domain.model.request.LoginRequest
import kr.co.medipanda.portal.domain.model.request.RefreshTokenRequest
import kr.co.medipanda.portal.domain.model.response.LoginResponse
import kr.co.medipanda.portal.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    ): ResponseEntity<LoginResponse> {
        return try {
            val loginResponse = authService.login(request)
            ResponseEntity.ok(loginResponse)
        } catch (_: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("/token/refresh")
    @Operation(summary = "AccessToken 재발급")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<LoginResponse> {
        return try {
            val response = authService.refreshToken(request.userId, request.refreshToken)
            ResponseEntity.ok(response)
        } catch (_: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @GetMapping("/public-key")
    @Operation(summary = "암호화용 공개키")
    fun getPublicKey(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf("publicKey" to authService.getPublicKey()))
    }

    @PostMapping("/verification-code/send/{userId}")
    @Operation(summary = "휴대폰 인증번호 전송")
    fun sendVerificationCode(
        @PathVariable userId: String,
    ) {
        //TODO: not implemented
        logger.info("Sending verification code")
    }

    @PostMapping("/verification-code/verify/{userId}")
    @Operation(summary = "휴대폰 인증번호 확인")
    fun verifyCode(
        @PathVariable userId: String,
        @RequestParam verificationCode: Int,
    ): Boolean {
        //TODO: not implemented
        return true
    }

    companion object : KLogging()
}