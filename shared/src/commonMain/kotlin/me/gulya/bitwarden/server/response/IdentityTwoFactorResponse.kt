package me.gulya.bitwarden.server.response

import me.gulya.bitwarden.enums.TwoFactorProviderType

data class IdentityTwoFactorResponse(
    val twoFactorProviders: List<TwoFactorProviderType>?,
    val twoFactorProviders2: Map<TwoFactorProviderType, Map<String, Any>>?,
)