package me.gulya.bitwarden.presentation

import me.gulya.bitwarden.domain.data.SymmetricCryptoKey

data class AttachmentView(
    val id: String?,
    val url: String?,
    val size: String?,
    val sizeName: String?,
    val key: SymmetricCryptoKey?,
    val fileName: String?,
) {

    val fileSize: Long by lazy {
        size?.toLongOrNull() ?: 0
    }
}