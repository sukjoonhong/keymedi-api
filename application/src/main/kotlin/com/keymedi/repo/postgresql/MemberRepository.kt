package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
    fun findByUserId(@Param("userId") userId: String): Member?
    fun existsByUserId(userId: String): Boolean
    fun existsByNickname(@Param("nickname") nickname: String): Boolean
}