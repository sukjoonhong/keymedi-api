package kr.co.medipanda.portal.domain.model.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)
