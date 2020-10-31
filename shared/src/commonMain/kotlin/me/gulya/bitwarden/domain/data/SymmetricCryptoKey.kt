package me.gulya.bitwarden.domain.data

import com.soywiz.krypto.encoding.Base64
import me.gulya.bitwarden.enums.EncryptionType
import okio.ByteString.Companion.toByteString

class SymmetricCryptoKey(
    val key: ByteArray,
    val encKey: ByteArray,
    val macKey: ByteArray?,
    val encType: EncryptionType,
    val keyB64: String,
    val encKeyB64: String,
    val macKeyB64: String?
) {

    companion object {

        operator fun invoke(key: ByteArray, encType: EncryptionType? = null): SymmetricCryptoKey {
            val encryptionType = encType ?: when (key.size) {
                32 -> EncryptionType.AES_CBC256_B64
                64 -> EncryptionType.AES_CBC256_HMAC_SHA256_BASE64
                else -> throw IllegalArgumentException("Unable to determine encType. Please specify it.")
            }
            val (encKey, macKey) =
                if (encryptionType === EncryptionType.AES_CBC256_B64 && key.size == 32) {
                    key to null
                } else if (encryptionType === EncryptionType.AES_CBC128_HMAC_SHA256_BASE64 && key.size == 32) {
                    val encKey = key.sliceArray(0 until 16)
                    val macKey = key.sliceArray(16 until 32)
                    encKey to macKey
                } else if (encryptionType === EncryptionType.AES_CBC256_HMAC_SHA256_BASE64 && key.size == 64) {
                    val encKey = key.sliceArray(0 until 32)
                    val macKey = key.sliceArray(32 until 64)
                    encKey to macKey
                } else {
                    throw IllegalArgumentException("Unsupported encType/key length.")
                }

            val encKeyB64 = encKey.toByteString().base64()
            val macKeyB64 = macKey?.let(Base64::encode)

            return SymmetricCryptoKey(
                key = key,
                keyB64 = key.toByteString().base64(),
                encKey = encKey,
                macKey = macKey,
                encType = encryptionType,
                encKeyB64 = encKeyB64,
                macKeyB64 = macKeyB64,
            )
        }

    }
}