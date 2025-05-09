package kr.co.medipanda.portal.domain.entity.postgresql

import kr.co.medipanda.portal.domain.MemberType
import jakarta.persistence.*

@Entity
@Table(
    name = "member",
    uniqueConstraints = [
        UniqueConstraint(name = "uq__member__user_id", columnNames = ["user_id"]),
        UniqueConstraint(name = "uq__member__nickname", columnNames = ["nickname"])
    ],
    indexes = [
        Index(name = "idx__member__user_id", columnList = "user_id")
    ]
)
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: String,
    val name: String,
    val nickname: String? = null,

    val password: String,
    val phoneNumber: String,
    val email: String,
    val referralCode: String? = null,
    val csoFilePath: String? = null,
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

