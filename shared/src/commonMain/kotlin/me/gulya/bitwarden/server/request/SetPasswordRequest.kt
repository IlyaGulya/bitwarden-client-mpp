package me.gulya.bitwarden.server.request

import me.gulya.bitwarden.enums.KeyDerivationFunctionType

data class SetPasswordRequest(
    val masterPasswordHash: String?,
    val key: String?,
    val masterPasswordHint: String?,
    val keys: KeysRequest?,
    val kdf: KeyDerivationFunctionType,
    val kdfIterations: Int = 0,
)