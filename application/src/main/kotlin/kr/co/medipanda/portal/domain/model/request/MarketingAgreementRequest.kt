package kr.co.medipanda.portal.domain.model.request

data class MarketingAgreementRequest(
    val sms: Boolean,
    val email: Boolean,
    val push: Boolean
)
