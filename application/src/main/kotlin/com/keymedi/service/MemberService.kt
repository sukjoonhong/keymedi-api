package com.keymedi.service

import com.keymedi.domain.MemberType
import com.keymedi.domain.entity.postgresql.Member
import com.keymedi.domain.model.request.MemberSignupRequest
import com.keymedi.repo.postgresql.MemberRepository
import com.keymedi.support.S3CsoFileUploader
import com.keymedi.utils.DEFAULT_NICK_NAME
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val s3CsoFileUploader: S3CsoFileUploader,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun signup(request: MemberSignupRequest, file: MultipartFile?) {
        if (memberRepository.existsByUserId(request.userId)) {
            throw IllegalArgumentException("user id already exists.")
        }

        val savedFilePath = file?.let { s3CsoFileUploader.upload(it) }

        val tempMember = Member(
            userId = request.userId,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            nickname = request.nickname,
            phoneNumber = request.phoneNumber,
            email = request.email,
            referralCode = request.referralCode,
            csoFilePath = savedFilePath,
            marketingAgreement = Member.MarketingAgreement(
                sms = request.marketingAgreement.sms,
                email = request.marketingAgreement.email,
                push = request.marketingAgreement.push
            ),
            memberType = MemberType.NONE
        )

        val savedMember = memberRepository.save(tempMember)

        val finalNickname = "$DEFAULT_NICK_NAME${savedMember.id}"
        memberRepository.save(savedMember.copy(nickname = finalNickname))
    }

    fun isUserIdAvailable(userId: String): Boolean {
        return !memberRepository.existsByUserId(userId)
    }

    fun isDuplicatedNickname(nickname: String): Boolean {
        return memberRepository.existsByNickname(nickname)
    }

    @Transactional
    fun updateNickname(userId: String, nickname: String) {
        val member = memberRepository.findByUserId(userId) ?: return
        memberRepository.save(member.copy(nickname = nickname))
    }
}