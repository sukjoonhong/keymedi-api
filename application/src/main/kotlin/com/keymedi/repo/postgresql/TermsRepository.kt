package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.Terms
import org.springframework.data.jpa.repository.JpaRepository

interface TermsRepository: JpaRepository<Terms, Long> {
    fun findTopByOrderByCreatedAtDesc(): Terms?
    fun findByVersion(version: String): Terms?
}