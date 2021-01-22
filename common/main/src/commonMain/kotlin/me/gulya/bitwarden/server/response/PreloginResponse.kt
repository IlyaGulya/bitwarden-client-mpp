package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.gulya.bitwarden.enums.KeyDerivationFunctionType

@Serializable
data class PreloginResponse(
    @SerialName("Kdf") val kdf: KeyDerivationFunctionType,
    @SerialName("KdfIterations") val kdfIterations: Int,
)