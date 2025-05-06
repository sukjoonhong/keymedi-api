package com.keymedi.web.v1

import com.keymedi.service.TermsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/terms")
@Tag(name = "약관 API")
class TermsController(
    private val termsService: TermsService
) {
    @GetMapping("/latest", produces = [MediaType.TEXT_HTML_VALUE])
    @Operation(summary = "최신 약관")
    fun getLatestTerms(): ResponseEntity<String> {
        return ResponseEntity.ok(termsService.getLatestTerms())
    }

    @GetMapping("/{version}", produces = [MediaType.TEXT_HTML_VALUE])
    @Operation(summary = "버전별 약관")
    fun getTermsByVersion(@PathVariable version: String): ResponseEntity<String> {
        return ResponseEntity.ok(termsService.getTermsByVersion(version))
    }
}
