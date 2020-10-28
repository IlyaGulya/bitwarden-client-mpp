package me.gulya.bitwarden.domain.login

import me.gulya.bitwarden.domain.data.SymmetricCryptoKey

class InMemoryKeyStorage : KeyStorage {

    override var sessionKey: SymmetricCryptoKey?

    override var encryptedEncryptionKey: SymmetricCryptoKey?

}