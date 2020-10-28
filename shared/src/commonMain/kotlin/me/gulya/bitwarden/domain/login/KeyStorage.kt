package me.gulya.bitwarden.domain.login

import me.gulya.bitwarden.domain.data.SymmetricCryptoKey

interface KeyStorage {
    var sessionKey: SymmetricCryptoKey?
    var encryptedEncryptionKey: SymmetricCryptoKey?
}

