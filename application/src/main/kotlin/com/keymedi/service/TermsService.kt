package com.keymedi.service

import com.keymedi.repo.postgresql.TermsRepository
import org.springframework.stereotype.Service

@Service
class TermsService(private val termsRepository: TermsRepository) {

    fun getLatestTerms(): String {
        return termsRepository.findTopByOrderByCreatedAtDesc()?.content
            ?: throw IllegalStateException("No terms found")
    }

    fun getTermsByVersion(version: String): String {
        return termsRepository.findByVersion(version)?.content
            ?: throw IllegalArgumentException("Version not found: $version")
    }
}