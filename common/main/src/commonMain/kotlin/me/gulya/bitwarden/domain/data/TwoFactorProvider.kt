package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.enums.TwoFactorProviderType

class TwoFactorProvider(
    val type: TwoFactorProviderType,
    val name: String?,
    val description: String?,
    val priority: Int,
    val sort: Int,
    val premium: Boolean,
)