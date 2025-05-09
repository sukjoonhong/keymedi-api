package kr.co.medipanda.portal.repo.postgresql

import kr.co.medipanda.portal.domain.entity.postgresql.Terms
import org.springframework.data.jpa.repository.JpaRepository

interface TermsRepository: JpaRepository<Terms, Long> {
    fun findTopByOrderByCreatedAtDesc(): Terms?
    fun findByVersion(version: String): Terms?
}