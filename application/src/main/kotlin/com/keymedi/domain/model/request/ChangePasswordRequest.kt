package com.keymedi.domain.model.request

data class ChangePasswordRequest(
    val userId: String,
    val currentPassword: String,
    val newPassword: String
)