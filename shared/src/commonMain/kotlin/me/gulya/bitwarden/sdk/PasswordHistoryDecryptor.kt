package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.PasswordHistory
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.PasswordHistoryView

class PasswordHistoryDecryptor(
    private val encryptionInteractor: EncryptionInteractor,
) {

    suspend fun decrypt(passwordHistory: PasswordHistory, key: SymmetricCryptoKey): PasswordHistoryView {
        return passwordHistory.run {
            PasswordHistoryView(
                password = encryptionInteractor.decryptUtf8(password, key),
                lastUsedDate = lastUsedDate,
            )
        }
    }

}