package kr.co.medipanda.portal.domain.model.request

data class LoginRequest(
    val userId: String,
    val password: String
)