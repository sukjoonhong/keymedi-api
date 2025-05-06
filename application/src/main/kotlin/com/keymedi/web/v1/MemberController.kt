package com.keymedi.web.v1

import com.keymedi.domain.model.request.ChangePasswordRequest
import com.keymedi.domain.model.request.MemberSignupRequest
import com.keymedi.service.AuthService
import com.keymedi.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/members")
@Tag(name = "회원 API")
class MemberController(
    private val memberService: MemberService,
    private val authService: AuthService,
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "회원가입")
    fun signup(
        @RequestPart("data") request: MemberSignupRequest,
        @RequestPart("file", required = false) csoFile: MultipartFile?
    ): ResponseEntity<Void> {
        memberService.signup(request, csoFile)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/available-nickname")
    @Operation(summary = "닉네임 중복 확인")
    fun isAvailableNickname(
        @RequestParam nickname: String
    ): ResponseEntity<Boolean> {
        val isDuplicated = memberService.isDuplicatedNickname(nickname)
        return ResponseEntity.ok(!isDuplicated)
    }

    @PatchMapping("/{userId}/nickname")
    @Operation(summary = "닉네임 변경")
    fun updateNickname(
        @PathVariable userId: String,
        @RequestParam nickname: String,
    ) {
        memberService.updateNickname(userId, nickname)
    }

    @PatchMapping("/{userId}/password")
    @Operation(summary = "비밀번호 변경")
    fun changePassword(
        @PathVariable userId: String,
        @RequestBody request: ChangePasswordRequest
    ): ResponseEntity<Void> {
        return try {
            authService.changePassword(request.copy(userId = userId))
            ResponseEntity.ok().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @GetMapping("/{userId}/available")
    @Operation(summary = "아이디 중복 체크")
    fun isUserIdAvailable(@PathVariable userId: String): Boolean {
        return memberService.isUserIdAvailable(userId)
    }
}