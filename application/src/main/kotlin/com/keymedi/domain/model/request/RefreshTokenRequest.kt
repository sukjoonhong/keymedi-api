package com.keymedi.domain.model.request

data class RefreshTokenRequest(
    val userId: String,
    val refreshToken: String
)
