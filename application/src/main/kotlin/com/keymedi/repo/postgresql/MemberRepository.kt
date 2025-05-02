package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
}