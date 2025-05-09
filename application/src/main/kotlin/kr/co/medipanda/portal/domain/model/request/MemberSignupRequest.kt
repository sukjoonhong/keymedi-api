package kr.co.medipanda.portal.domain.model.request

data class MemberSignupRequest (
    val userId: String,
    val password: String,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val nickname: String? = null,
    val referralCode: String?,
    val marketingAgreement: MarketingAgreementRequest,
)