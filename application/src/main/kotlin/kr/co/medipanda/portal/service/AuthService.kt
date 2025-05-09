package kr.co.medipanda.portal.service

import kr.co.medipanda.portal.domain.entity.postgresql.Member
import kr.co.medipanda.portal.domain.model.request.ChangePasswordRequest
import kr.co.medipanda.portal.domain.model.request.LoginRequest
import kr.co.medipanda.portal.domain.model.response.LoginResponse
import kr.co.medipanda.portal.repo.postgresql.MemberRepository
import kr.co.medipanda.portal.security.JwtService
import kr.co.medipanda.portal.security.RsaUtil
import jakarta.transaction.Transactional
import mu.KLogging
import org.springframework.core.env.Environment
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val environment: Environment,
    private val memberRepository: MemberRepository,
    private val rsaUtil: RsaUtil,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {
    fun getPublicKey(): String {
        return rsaUtil.getPublicKeyAsString()
    }

    @Transactional
    fun changePassword(request: ChangePasswordRequest) {
        val member = findByUserId(request.userId)

        val decryptedCurrentPassword = if (isLocalProfile()) {
            request.currentPassword
        } else {
            rsaUtil.decrypt(request.currentPassword)
        }

        val decryptedNewPassword = if (isLocalProfile()) {
            request.newPassword
        } else {
            rsaUtil.decrypt(request.newPassword)
        }

        if (!passwordEncoder.matches(decryptedCurrentPassword, member.password)) {
            throw IllegalArgumentException("the current password is incorrect")
        }

        val encryptedNewPassword = passwordEncoder.encode(decryptedNewPassword)

        memberRepository.save(member.copy(password = encryptedNewPassword))
    }

    @Transactional
    fun login(loginRequest: LoginRequest): LoginResponse {
        val decryptedPassword = if (isLocalProfile()) {
            loginRequest.password
        } else {
            rsaUtil.decrypt(loginRequest.password)
        }

        val member = findByUserId(loginRequest.userId)

        if (!passwordEncoder.matches(decryptedPassword, member.password)) {
            throw IllegalArgumentException("incorrect password")
        }

        val accessToken = jwtService.generateAccessToken(member.userId)
        val refreshToken = jwtService.generateRefreshToken(member.userId)

        memberRepository.save(member.copy(refreshToken = refreshToken))

        return LoginResponse(accessToken = accessToken, refreshToken = refreshToken)
    }

    @Transactional
    fun refreshToken(userId: String, requestRefreshToken: String): LoginResponse {
        val member = findByUserId(userId)

        if (member.refreshToken != requestRefreshToken) {
            throw IllegalArgumentException("Invalid refresh token")
        }

        if (!jwtService.validateToken(requestRefreshToken)) {
            throw IllegalArgumentException("Expired or malformed refresh token")
        }

        val newAccessToken = jwtService.generateAccessToken(member.userId)
        val newRefreshToken = jwtService.generateRefreshToken(member.userId)

        memberRepository.save(member.copy(refreshToken = newRefreshToken))

        return LoginResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    private fun findByUserId(userId: String): Member {
        try {
            // spring security 가 UsernameNotFoundException 내부적으로 처리해서
            // try-catch 안 묶으면 exception 먹음
            return memberRepository.findByUserId(userId)
                ?: throw UsernameNotFoundException("member not found for userId: $userId")
        } catch (e: UsernameNotFoundException) {
            logger.error(e) { "authentication failed: " }
            throw e
        }
    }

    fun isLocalProfile() = environment.activeProfiles.contains("local")

    companion object : KLogging()
}