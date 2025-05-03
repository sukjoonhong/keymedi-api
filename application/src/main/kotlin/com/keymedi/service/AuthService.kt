package com.keymedi.service

import com.keymedi.domain.entity.postgresql.Member
import com.keymedi.domain.model.LoginRequest
import com.keymedi.repo.postgresql.MemberRepository
import com.keymedi.security.JwtService
import com.keymedi.security.RsaUtil
import mu.KLogging
import org.springframework.core.env.Environment
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val environment: Environment,
    private val memberRepository: MemberRepository,
    private val rsaUtil: RsaUtil,
    private val jwtService: JwtService,
) {
    fun login(loginRequest: LoginRequest): String {
        val decryptedPassword = if (isLocalProfile()) {
            loginRequest.password
        } else {
            rsaUtil.decrypt(loginRequest.password)
        }
        val member = findByAuthId(loginRequest.authId)

        if (!BCryptPasswordEncoder().matches(decryptedPassword, member.password)) {
            throw IllegalArgumentException("incorrect password")
        }
        return jwtService.generateAccessToken(member.authId)
    }

    private fun findByAuthId(authId: String): Member {
        try {
            // spring security 가 UsernameNotFoundException 내부적으로 처리해서
            // try-catch 안 묶으면 exception 먹음
            return memberRepository.findByAuthId(authId)
                ?: throw UsernameNotFoundException("member not found for authId: $authId")
        } catch (e: UsernameNotFoundException) {
            logger.error(e) { "authentication failed: " }
            throw e
        }
    }

    fun isLocalProfile() = environment.activeProfiles.contains("local")

    companion object : KLogging()
}