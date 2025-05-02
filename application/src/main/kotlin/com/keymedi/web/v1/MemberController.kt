package com.keymedi.web.v1

import com.keymedi.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/members")
@Tag(name = "회원 API")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping
    @Operation(summary = "회원 등록")
    fun create(@RequestBody request: MemberCreateRequest): MemberResponse {
        TODO("not implemented")
    }

    @GetMapping("/{id}")
    @Operation(summary = "회원 단건 조회")
    fun get(@PathVariable id: Long): MemberResponse {
        TODO("not implemented")
    }

    @PutMapping("/{id}")
    @Operation(summary = "회원 수정")
    fun update(@PathVariable id: Long, @RequestBody request: MemberUpdateRequest): MemberResponse {
        TODO("not implemented")
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "회원 삭제")
    fun delete(@PathVariable id: Long) {
        TODO("not implemented")
    }

    data class MemberCreateRequest(val id: Long)
    data class MemberUpdateRequest(val id: Long)
    data class MemberResponse(val id: Long)
}