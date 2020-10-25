package me.gulya.bitwarden.server.request

data class PasswordVerificationRequest(
    val masterPasswordHash: String
)