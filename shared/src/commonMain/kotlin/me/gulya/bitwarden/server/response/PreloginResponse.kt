package me.gulya.bitwarden.server.response

import me.gulya.bitwarden.enums.KeyDerivationFunctionType

data class PreloginResponse(
    val kdf: KeyDerivationFunctionType,
    val kdfIterations: Int,
)