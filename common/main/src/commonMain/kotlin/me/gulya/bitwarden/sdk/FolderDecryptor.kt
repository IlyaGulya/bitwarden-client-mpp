package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.Folder
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.FolderView

class FolderDecryptor(
    private val encryptionInteractor: EncryptionInteractor,
) {
    suspend fun decrypt(folder: Folder, key: SymmetricCryptoKey): FolderView {
        return folder.run {
            FolderView(
                id = id,
                name = encryptionInteractor.decryptUtf8(name, key),
                revisionDate = revisionDate,
            )
        }
    }
}