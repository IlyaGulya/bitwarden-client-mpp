package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.enums.EncryptionType
import kotlin.jvm.JvmName

sealed class EncryptedString {
    abstract val encryptionType: EncryptionType
    abstract val encryptedString: String
    abstract val data: String

    data class AesEncryptedString(
        override val encryptedString: String,
        override val encryptionType: EncryptionType,
        override val data: String,
        val initializationVector: String,
        val messageAuthCode: String?,
    ) : EncryptedString()

    data class RsaEncryptedString(
        override val encryptedString: String,
        override val encryptionType: EncryptionType,
        override val data: String,
    ) : EncryptedString()

    companion object {

        operator fun invoke(
            encryptionType: EncryptionType,
            data: String,
            initializationVector: String? = null,
            messageAuthCode: String? = null
        ): EncryptedString {
            if (data.isBlank()) {
                throw IllegalArgumentException("Data is empty")
            }
            val payload =
                listOfNotNull(
                    initializationVector,
                    data,
                    messageAuthCode
                )
                    .filter { it.isNotBlank() }
                    .joinToString("|")

            val encryptedString = "${encryptionType.value}.$payload"

            return when (encryptionType) {
                EncryptionType.AES_CBC256_B64,
                EncryptionType.AES_CBC128_HMAC_SHA256_BASE64,
                EncryptionType.AES_CBC256_HMAC_SHA256_BASE64 -> {
                    AesEncryptedString(
                        encryptedString = encryptedString,
                        encryptionType = encryptionType,
                        data = data,
                        initializationVector = initializationVector
                            ?: throw IllegalArgumentException("AES requires IV"),
                        messageAuthCode = messageAuthCode,
                    )
                }
                EncryptionType.RSA2048_OAEP_SHA1_BASE64,
                EncryptionType.RSA2048_OAEP_SHA1_HMAC_SHA256_BASE64,
                EncryptionType.RSA2048_OAEP_SHA256_BASE64,
                EncryptionType.RSA2048_OAEP_SHA256_HMAC_SHA256_BASE64 -> {
                    RsaEncryptedString(
                        encryptedString = encryptedString,
                        encryptionType = encryptionType,
                        data = data,
                    )
                }
            }
        }

        operator fun invoke(encryptedString: String): EncryptedString {
            if (encryptedString.isBlank()) {
                throw IllegalArgumentException("encryptedString is blank")
            }
            val headerData = encryptedString.split(".")
            val (encryptionType, payload) =
                if (headerData.size == 2) {
                    val encryptionType = EncryptionType.forValue(headerData[0].toByte())
                    val payload = headerData[1].split("|")
                    encryptionType to payload
                } else {
                    val payload = encryptedString.split("|")
                    val encryptionType =
                        if (payload.size == 3) {
                            EncryptionType.AES_CBC128_HMAC_SHA256_BASE64
                        } else {
                            EncryptionType.AES_CBC256_B64
                        }
                    encryptionType to payload
                }
            if (payload.isEmpty()) {
                throw IllegalArgumentException("Payload is empty!")
            }
            when (encryptionType) {
                EncryptionType.AES_CBC128_HMAC_SHA256_BASE64, EncryptionType.AES_CBC256_HMAC_SHA256_BASE64 -> {
                    if (payload.size == 3) {
                        return EncryptedString(
                            encryptionType = encryptionType,
                            initializationVector = payload[0],
                            data = payload[1],
                            messageAuthCode = payload[2],
                        )
                    } else {
                        throw IllegalArgumentException("Encryption type $encryptionType needs IV and MAC")
                    }
                }
                EncryptionType.AES_CBC256_B64 -> {
                    if (payload.size == 2) {
                        return EncryptedString(
                            encryptionType = encryptionType,
                            initializationVector = payload[0],
                            data = payload[1],
                            messageAuthCode = null,
                        )
                    } else {
                        throw IllegalArgumentException("Encryption type $encryptionType needs IV")
                    }
                }
                EncryptionType.RSA2048_OAEP_SHA256_BASE64,
                EncryptionType.RSA2048_OAEP_SHA1_BASE64 -> {
                    return EncryptedString(
                        encryptionType = encryptionType,
                        initializationVector = null,
                        data = payload[0],
                        messageAuthCode = null,
                    )
                }
                else -> throw IllegalArgumentException("Encryption type $encryptionType is not supported!")
            }
        }
    }

}


@JvmName("toCipherStringNullable")
fun String?.toCipherString(): EncryptedString? = this?.let(EncryptedString::invoke)
fun String.toCipherString() = EncryptedString(this)