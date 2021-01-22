package me.gulya.bitwarden.sdk

import com.github.aakira.napier.Napier
import me.gulya.bitwarden.domain.data.Attachment
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.AttachmentView

class AttachmentDecryptor(
    private val encryptionInteractor: EncryptionInteractor,
) {
    suspend fun decrypt(attachment: Attachment, key: SymmetricCryptoKey): AttachmentView {
        return attachment.run {
            val decryptedKey = try {
                encryptionInteractor.decryptBytes(this.key, key)
            } catch (e: Exception) {
                Napier.e("Unable to decode")
                null
            }

            AttachmentView(
                id = id,
                url = url,
                size = size,
                sizeName = sizeName,
                key = decryptedKey?.let { SymmetricCryptoKey(decryptedKey) },
                fileName = encryptionInteractor.decryptUtf8(fileName, key),
            )
        }
    }

}