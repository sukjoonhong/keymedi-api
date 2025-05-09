package kr.co.medipanda.portal.domain.model.request

data class RefreshTokenRequest(
    val userId: String,
    val refreshToken: String
)
