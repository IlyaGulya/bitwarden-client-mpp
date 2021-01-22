package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.crypto.Crypto
import me.gulya.bitwarden.domain.data.EncryptedString
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.domain.login.KeyStorage
import me.gulya.bitwarden.enums.EncryptionType
import okio.ByteString.Companion.decodeBase64

class KeyInteractor(
    private val keyStorage: KeyStorage,
    private val crypto: Crypto,
) {

    suspend fun getEncryptionKey(): SymmetricCryptoKey? {
        val encryptedKey = keyStorage.getEncryptedKey() ?: return null
        val sessionKeyBytes = keyStorage.getSessionKey()?.key?.decodeBase64()?.toByteArray() ?: return null
        val sessionKey = SymmetricCryptoKey(sessionKeyBytes)
        val keyEncryptedString = EncryptedString(encryptedKey.key)
        val decryptedEncryptionKey =
            when (keyEncryptedString.encryptionType) {
                EncryptionType.AES_CBC256_B64 -> {
                    crypto.decryptToBytes(keyEncryptedString, sessionKey)
                }
                EncryptionType.AES_CBC256_HMAC_SHA256_BASE64 -> {
                    val newKey = crypto.stretchKey(sessionKey)
                    crypto.decryptToBytes(keyEncryptedString, newKey)
                }
                else -> {
                    throw IllegalArgumentException("Unsupported encryption key type")
                }
            }

        return if (decryptedEncryptionKey != null) {
            SymmetricCryptoKey(decryptedEncryptionKey)
        } else {
            null
        }
    }

}