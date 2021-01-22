package me.gulya.bitwarden.crypto

import me.gulya.bitwarden.domain.data.EncryptedString

data class RsaKeyPair(
    val publicKey: String,
    val privateKey: EncryptedString,
)