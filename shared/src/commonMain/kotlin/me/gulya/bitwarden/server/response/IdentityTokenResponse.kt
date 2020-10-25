package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import me.gulya.bitwarden.enums.KeyDerivationFunctionType

data class IdentityTokenResponse(
    @SerialName("access_token")
    val accessToken: String?,

    @SerialName("expires_in")
    val expiresIn: String?,

    @SerialName("refresh_token")
    val refreshToken: String?,

    @SerialName("token_type")
    val tokenType: String?,
    var resetMasterPassword: Boolean,
    val privateKey: String?,
    val key: String?,
    val twoFactorToken: String?,
    var kdf: KeyDerivationFunctionType,
    var kdfIterations: Int,
)