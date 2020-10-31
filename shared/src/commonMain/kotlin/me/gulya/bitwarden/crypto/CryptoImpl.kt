package me.gulya.bitwarden.crypto

import com.github.aakira.napier.Napier
import com.soywiz.krypto.encoding.base64
import me.gulya.bitwarden.domain.data.EncryptedString
import me.gulya.bitwarden.domain.data.EncryptedString.AesEncryptedString
import me.gulya.bitwarden.domain.data.EncryptedString.RsaEncryptedString
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.enums.CryptoHashAlgorithm
import me.gulya.bitwarden.enums.EncryptionType
import me.gulya.bitwarden.enums.KeyDerivationFunctionType
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.toByteString
import kotlin.math.ceil

class CryptoImpl(
    private val cryptoFunctions: CryptoFunctions
) : Crypto {
    companion object {
        const val PBKDF2_MIN_ITERATIONS = 5000
    }

    override suspend fun hashPassword(password: String, key: SymmetricCryptoKey): String {
        return cryptoFunctions.pbkdf2(key.key, password, CryptoHashAlgorithm.SHA256, 1).base64
    }

    override suspend fun createKey(
        password: String,
        salt: String,
        kdf: KeyDerivationFunctionType,
        iterations: Int
    ): SymmetricCryptoKey {
        if (iterations < PBKDF2_MIN_ITERATIONS) throw IllegalArgumentException("PBKDF2 has to have at least $PBKDF2_MIN_ITERATIONS iterations")
        val key = cryptoFunctions.pbkdf2(password, salt, CryptoHashAlgorithm.SHA256, iterations)
        return SymmetricCryptoKey(key)
    }

    override suspend fun encrypt(value: ByteArray, key: SymmetricCryptoKey): EncryptedString {
        val encryptedValue = aesEncrypt(value, key)
        return encryptedValue.run {
            EncryptedString(
                encryptionType = key.encType,
                data = data.toByteString().base64(),
                initializationVector = initializationVector.toByteString().base64(),
                messageAuthCode = messageAuthCode?.toByteString()?.base64()
            )

        }
    }

    override suspend fun createKeyPair(key: SymmetricCryptoKey): RsaKeyPair {
        val keyPair = cryptoFunctions.generateRsaKeyPair(RsaKeyLength.RSA2048)
        return keyPair.run {
            RsaKeyPair(
                publicKey = public.toByteString().base64(),
                privateKey = encrypt(private, key)
            )
        }
    }

    override suspend fun decryptToUtf8(encryptedString: EncryptedString, key: SymmetricCryptoKey): String? {
        return when (encryptedString) {
            is AesEncryptedString -> aesDecrypt(
                encryptedString = encryptedString,
                key = key
            )?.toByteString()?.utf8()
            is RsaEncryptedString -> throw IllegalArgumentException("RSA to UTF8 is not supported yet.")
        }
    }

    override suspend fun decryptToBytes(encryptedString: EncryptedString, key: SymmetricCryptoKey): ByteArray? {
        return when (encryptedString) {
            is AesEncryptedString -> aesDecrypt(
                encryptedString = encryptedString,
                key = key
            )
            is RsaEncryptedString -> throw IllegalArgumentException("RSA to bytes is not supported yet.")
        }
    }

    override suspend fun stretchKey(sessionKey: SymmetricCryptoKey): SymmetricCryptoKey {
        val enc = hkdfExpand(sessionKey.key, "enc".encodeToByteArray(), 32)
        val mac = hkdfExpand(sessionKey.key, "mac".encodeToByteArray(), 32)
        val newKey = enc + mac
        return SymmetricCryptoKey(newKey)
    }

    // https://tools.ietf.org/html/rfc5869
    private suspend fun hkdfExpand(prk: ByteArray, info: ByteArray, size: Int): ByteArray {
        val hashLength = 32 // SHA256
        val okm = ByteArray(size)
        var previousT = ByteArray(0)
        val n = ceil(size.toDouble() / hashLength.toDouble()).toInt()

        (0 until n).forEach { i ->
            val t = ByteArray(previousT.size + info.size + 1)
            previousT.copyInto(t)
            info.copyInto(t, previousT.size)
            t[t.lastIndex] = (i + 1).toByte()
            previousT = cryptoFunctions.hmac(t, prk, CryptoHashAlgorithm.SHA256)
            previousT.copyInto(okm, i * hashLength)
        }

        return okm
    }

    private suspend fun aesDecrypt(
        encryptedString: AesEncryptedString,
        key: SymmetricCryptoKey,
    ): ByteArray? {
        val encryptionType: EncryptionType = encryptedString.encryptionType
        val data: String = encryptedString.data
        val initializationVector: String = encryptedString.initializationVector
        val messageAuthCode: String? = encryptedString.messageAuthCode

        val validKey = maybeLegacyKey(encryptionType, key)

        if (validKey.macKey != null && messageAuthCode == null) {
            Napier.e("MAC is required to decrypt")
            return null
        }

        if (validKey.encType != encryptionType) {
            Napier.e("Encryption type of key ${validKey.encType} does not match requested encrytion type $encryptionType")
            return null
        }

        val encryptionKey = validKey.encKey
        val dataBytes = data.decodeBase64()?.toByteArray()

        if (dataBytes == null) {
            Napier.e("Unable to decode data as Base64")
            return null
        }

        val initializationVectorBytes = initializationVector.decodeBase64()?.toByteArray()

        if (initializationVectorBytes == null) {
            Napier.e("Unable to decode initialization vector as Base64")
            return null
        }

        val ourMacBytes = initializationVectorBytes + dataBytes
        val macKey = validKey.macKey
        val macBytes = messageAuthCode?.decodeBase64()?.toByteArray()

        if (macKey != null && messageAuthCode != null) {
            val computedMac = cryptoFunctions.hmac(ourMacBytes, macKey, CryptoHashAlgorithm.SHA256)
            if (!macBytes.contentEquals(computedMac)) {
                Napier.e("Message authentication failed")
                return null
            }
        }

        return cryptoFunctions.decryptAes(dataBytes, initializationVectorBytes, encryptionKey)
    }

    private suspend fun maybeLegacyKey(type: EncryptionType, key: SymmetricCryptoKey): SymmetricCryptoKey {
        if (type == EncryptionType.AES_CBC128_HMAC_SHA256_BASE64 && key.encType == EncryptionType.AES_CBC256_B64) {
            return SymmetricCryptoKey(key.key, EncryptionType.AES_CBC128_HMAC_SHA256_BASE64)
        }
        return key
    }

    private suspend fun aesEncrypt(value: ByteArray, key: SymmetricCryptoKey): EncryptedValue {
        val initializationVector = cryptoFunctions.randomBytes(16)
        val encryptedData = cryptoFunctions.encryptAes(value, initializationVector, key.encKey)
        return EncryptedValue(
            key = key,
            data = encryptedData,
            initializationVector = initializationVector,
            messageAuthCode = key.macKey?.let { macKey ->
                val macData = initializationVector + encryptedData
                cryptoFunctions.hmac(macData, macKey, CryptoHashAlgorithm.SHA256)
            }
        )
    }

}

class EncryptedValue(
    val key: SymmetricCryptoKey,
    val data: ByteArray,
    val initializationVector: ByteArray,
    val messageAuthCode: ByteArray?
)