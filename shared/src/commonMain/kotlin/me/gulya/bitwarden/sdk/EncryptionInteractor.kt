package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.crypto.Crypto
import me.gulya.bitwarden.domain.data.EncryptedString
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import kotlin.jvm.JvmName

class EncryptionInteractor(
    private val crypto: Crypto
) {
    @JvmName("decryptUtf8Nullable")
    suspend fun decryptUtf8(encryptedString: EncryptedString?, key: SymmetricCryptoKey): String? {
        return encryptedString?.let { decryptUtf8(encryptedString, key) }
    }

    suspend fun decryptUtf8(encryptedString: EncryptedString, key: SymmetricCryptoKey): String? {
        return crypto.decryptToUtf8(encryptedString, key)
    }

    @JvmName("decryptBytesNullable")
    suspend fun decryptBytes(encryptedString: EncryptedString?, key: SymmetricCryptoKey): ByteArray? {
        return encryptedString?.let { decryptBytes(encryptedString, key) }
    }

    suspend fun decryptBytes(encryptedString: EncryptedString, key: SymmetricCryptoKey): ByteArray? {
        return crypto.decryptToBytes(encryptedString = encryptedString, key = key)
    }


}