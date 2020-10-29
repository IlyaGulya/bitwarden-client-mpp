package me.gulya.bitwarden.crypto

import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.enums.KeyDerivationFunctionType


interface Crypto {

    suspend fun hashPassword(password: String, key: SymmetricCryptoKey): String

    suspend fun createKey(
        password: String,
        salt: String,
        kdf: KeyDerivationFunctionType,
        iterations: Int
    ): SymmetricCryptoKey

    suspend fun createKeyPair(): KeyPair

}

