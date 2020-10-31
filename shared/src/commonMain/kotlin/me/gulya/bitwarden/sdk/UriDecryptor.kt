package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.LoginUri
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.LoginUriView

class UriDecryptor(
    private val encryptionInteractor: EncryptionInteractor
) {

    suspend fun decrypt(loginUri: LoginUri, key: SymmetricCryptoKey): LoginUriView {
        return loginUri.run {
            LoginUriView(
                uri = encryptionInteractor.decryptUtf8(uri, key),
                matchType = matchType,
            )
        }
    }

}