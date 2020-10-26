package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.gulya.bitwarden.enums.TwoFactorProviderType

@Serializable
data class IdentityTwoFactorResponse(
    @SerialName("TwoFactorProviders") val twoFactorProviders: List<TwoFactorProviderType>,
    @SerialName("TwoFactorProviders2") val twoFactorProviders2: Map<TwoFactorProviderType, Map<String, String>>,
)