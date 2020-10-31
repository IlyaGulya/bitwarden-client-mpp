package me.gulya.bitwarden.crypto

import me.gulya.bitwarden.domain.data.EncryptedString
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

    suspend fun encrypt(
        value: ByteArray,
        key: SymmetricCryptoKey
    ): EncryptedString

    suspend fun createKeyPair(key: SymmetricCryptoKey): RsaKeyPair

    suspend fun decryptToUtf8(encryptedString: EncryptedString, key: SymmetricCryptoKey): String?

    suspend fun decryptToBytes(encryptedString: EncryptedString, key: SymmetricCryptoKey): ByteArray?
    suspend fun stretchKey(sessionKey: SymmetricCryptoKey): SymmetricCryptoKey

}

