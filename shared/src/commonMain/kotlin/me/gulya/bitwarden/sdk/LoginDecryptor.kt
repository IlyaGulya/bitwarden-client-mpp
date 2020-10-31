package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.Login
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.LoginView

class LoginDecryptor(
    private val encryptionInteractor: EncryptionInteractor,
    private val uriDecryptor: UriDecryptor,
) {
    suspend fun decrypt(login: Login, key: SymmetricCryptoKey): LoginView {
        return login.run {
            LoginView(
                username = encryptionInteractor.decryptUtf8(username, key),
                password = encryptionInteractor.decryptUtf8(password, key),
                passwordRevisionDate = passwordRevisionDate,
                totp = encryptionInteractor.decryptUtf8(totp, key),
                uris = uris.map { uri -> uriDecryptor.decrypt(uri, key) }
            )
        }
    }
}