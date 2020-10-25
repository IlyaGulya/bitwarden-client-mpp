package me.gulya.bitwarden.enums

enum class EncryptionType(
    override val value: Byte,
): Valued<Byte> {
    AES_CBC256_B64(0),
    AES_CBC128_HMAC_SHA256_BASE64(1),
    AES_CBC256_HMAC_SHA256_BASE64(2),
    RSA2048_OAEP_SHA256_BASE64(3),
    RSA2048_OAEP_SHA1_BASE64(4),
    RSA2048_OAEP_SHA256_HMAC_SHA256_BASE64(5),
    RSA2048_OAEP_SHA1_HMAC_SHA256_BASE64(6);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}