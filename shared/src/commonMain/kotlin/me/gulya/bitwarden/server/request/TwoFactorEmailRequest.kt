package me.gulya.bitwarden.server.request

data class TwoFactorEmailRequest(
    val email: String,
    val masterPasswordHash: String,
)