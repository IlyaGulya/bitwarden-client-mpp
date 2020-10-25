package me.gulya.bitwarden.server.request

import me.gulya.bitwarden.enums.KeyDerivationFunctionType

data class RegisterRequest(
    val name: String?,
    val email: String?,
    val masterPasswordHash: String?,
    val masterPasswordHint: String?,
    val key: String?,
    val keys: KeysRequest?,
    val token: String?,
    val kdf: KeyDerivationFunctionType?,
    val kdfIterations: Int?,
    val organizationUserId: String,
)