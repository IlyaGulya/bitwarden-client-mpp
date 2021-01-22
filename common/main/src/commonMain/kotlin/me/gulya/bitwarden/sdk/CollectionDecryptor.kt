package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.Collection
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.CollectionView

class CollectionDecryptor(
    private val encryptionInteractor: EncryptionInteractor,
) {
    suspend fun decrypt(collection: Collection, key: SymmetricCryptoKey): CollectionView {
        return collection.run {
            CollectionView(
                id = id,
                name = encryptionInteractor.decryptUtf8(name, key),
                organizationId = organizationId,
                externalId = externalId,
                readOnly = readOnly,
            )
        }
    }
}