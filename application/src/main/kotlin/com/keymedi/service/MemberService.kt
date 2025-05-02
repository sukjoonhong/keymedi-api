package com.keymedi.service

import com.keymedi.repo.postgresql.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
}