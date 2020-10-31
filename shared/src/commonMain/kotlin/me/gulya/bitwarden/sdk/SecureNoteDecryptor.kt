package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.SecureNote
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.SecureNoteView

class SecureNoteDecryptor(
    private val encryptionInteractor: EncryptionInteractor
) {

    suspend fun decrypt(secureNote: SecureNote, key: SymmetricCryptoKey): SecureNoteView {
        return secureNote.run {
            SecureNoteView(
                type = type
            )
        }
    }

}