package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.Field
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.FieldView

class FieldDecryptor(
    private val encryptionInteractor: EncryptionInteractor,
) {

    suspend fun decrypt(field: Field, key: SymmetricCryptoKey): FieldView {
        return field.run {
            FieldView(
                name = encryptionInteractor.decryptUtf8(name, key),
                value = encryptionInteractor.decryptUtf8(value, key),
                type = type,
            )
        }
    }
}