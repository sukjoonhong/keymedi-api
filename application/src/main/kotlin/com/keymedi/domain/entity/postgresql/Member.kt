package com.keymedi.domain.entity.postgresql

import com.keymedi.domain.MemberType
import jakarta.persistence.*

@Entity
@Table(
    name = "member",
    uniqueConstraints = [
        UniqueConstraint(name = "uq__member__auth_id", columnNames = ["auth_id"]),
        UniqueConstraint(name = "uq__member__nickname", columnNames = ["nickname"])
    ],
    indexes = [
        Index(name = "idx__member__auth_id", columnList = "auth_id")
    ]
)
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "auth_id", nullable = false)
    val authId: String,
    val name: String,
    val nickname: String,
    val password: String,
    val phoneNumber: String,
    val email: String,
    val recommenderCode: String? = null,
    val deleted: Boolean = false,

    @Column(nullable = false)
    @Embedded
    val marketingAgreement: MarketingAgreement = MarketingAgreement(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val memberType: MemberType = MemberType.NONE,

    val refreshToken: String? = null
) : BaseEntity() {

    @Embeddable
    data class MarketingAgreement(
        @Column(name = "marketing_push_agree", nullable = false)
        val push: Boolean = false,

        @Column(name = "marketing_sms_agree", nullable = false)
        val sms: Boolean = false,

        @Column(name = "marketing_email_agree", nullable = false)
        val email: Boolean = false
    )
}

