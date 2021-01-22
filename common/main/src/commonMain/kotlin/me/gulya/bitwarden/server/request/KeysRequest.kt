package me.gulya.bitwarden.server.request

data class KeysRequest(
    val publicKey: String,
    val encryptedPrivateKey: String,
)