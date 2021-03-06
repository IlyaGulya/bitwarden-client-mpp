package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.enums.TwoFactorProviderType

data class AuthResult(
    val twoFactor: Boolean,
    val resetMasterPassword: Boolean,
    val twoFactorProviders: Map<TwoFactorProviderType, Map<String, Any>>?,
)