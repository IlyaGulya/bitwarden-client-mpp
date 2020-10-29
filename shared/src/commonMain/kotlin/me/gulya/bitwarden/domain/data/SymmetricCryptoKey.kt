package me.gulya.bitwarden.domain.data

import com.soywiz.krypto.encoding.Base64
import me.gulya.bitwarden.enums.EncryptionType
import okio.ByteString.Companion.toByteString

class SymmetricCryptoKey(
    val key: ByteArray,
    var keyB64: String,
    val encType: EncryptionType = EncryptionType.values()[0],
    var encKey: ByteArray,
    var macKey: ByteArray?,
    var encKeyB64: String,
    var macKeyB64: String? = null
) {

    constructor(key: ByteArray, encType: EncryptionType? = null) : this(
        key = key,
        encType = encType ?: when (key.size) {
            32 -> EncryptionType.AES_CBC256_B64
            64 -> EncryptionType.AES_CBC256_HMAC_SHA256_BASE64
            else -> throw IllegalArgumentException("Unable to determine encType. Please specify it.")
        },
        keyB64 = Base64.encode(key),
    )

    init {
        if (encType === EncryptionType.AES_CBC256_B64 && key.size == 32) {
            encKey = key
            macKey = null
        } else if (encType === EncryptionType.AES_CBC128_HMAC_SHA256_BASE64 && key.size == 32) {
            encKey = key.sliceArray(0..16)
            macKey = key.sliceArray(16..32)
        } else if (encType === EncryptionType.AES_CBC256_HMAC_SHA256_BASE64 && key.size == 64) {
            encKey = key.sliceArray(0..32)
            macKey = key.sliceArray(32..64)
        } else {
            throw IllegalArgumentException("Unsupported encType/key length.")
        }

        encKeyB64 = encKey.toByteString().base64()
        macKeyB64 = macKey?.let(Base64::encode)
    }
}