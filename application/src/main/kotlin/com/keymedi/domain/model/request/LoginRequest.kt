package com.keymedi.domain.model.request

data class LoginRequest(
    val userId: String,
    val password: String
)