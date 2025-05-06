package com.keymedi.domain.model.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)
