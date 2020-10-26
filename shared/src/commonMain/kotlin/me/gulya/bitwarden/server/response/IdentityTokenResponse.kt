package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.gulya.bitwarden.enums.KeyDerivationFunctionType

@Serializable
data class IdentityTokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("token_type") val tokenType: String? = null,
    @SerialName("ResetMasterPassword") val resetMasterPassword: Boolean,
    @SerialName("PrivateKey") val privateKey: String? = null,
    @SerialName("Key") val key: String? = null,
    @SerialName("TwoFactorToken") val twoFactorToken: String? = null,
    @SerialName("Kdf") val kdf: KeyDerivationFunctionType,
    @SerialName("KdfIterations") val kdfIterations: Int,
)