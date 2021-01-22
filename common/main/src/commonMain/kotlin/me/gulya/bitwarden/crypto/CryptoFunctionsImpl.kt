package me.gulya.bitwarden.crypto

import com.soywiz.krypto.AES
import com.soywiz.krypto.HMAC
import com.soywiz.krypto.PBKDF2
import com.soywiz.krypto.Padding
import me.gulya.bitwarden.enums.CryptoHashAlgorithm

class CryptoFunctionsImpl(
    private val primitives: CryptoPrimitives
) : CryptoFunctions {
    override suspend fun pbkdf2(
        password: ByteArray,
        salt: ByteArray,
        hashAlgorithm: CryptoHashAlgorithm,
        iterations: Int
    ): ByteArray {
        if (hashAlgorithm != CryptoHashAlgorithm.SHA256 && hashAlgorithm != CryptoHashAlgorithm.SHA512) {
            throw IllegalArgumentException("Unsupported PBKDF2 algorithm: $hashAlgorithm")
        }

        return when (hashAlgorithm) {
            CryptoHashAlgorithm.SHA256 -> PBKDF2.pbkdf2WithHmacSHA256(
                password = password,
                salt = salt,
                iterationCount = iterations,
                keySizeInBits = 256
            )
            CryptoHashAlgorithm.SHA512 -> TODO("PBKDF2 with SHA512 is not supported by crypto library yet")
            else -> throw IllegalArgumentException("Digest $hashAlgorithm is not supported by PBKDF2")
        }
    }

    override suspend fun generateRsaKeyPair(length: RsaKeyLength): AsymmetricKeyPair {
        return primitives.generateRsaKeyPair(length)
    }

    override suspend fun randomBytes(numBytes: Int): ByteArray {
        return primitives.randomBytes(numBytes)
    }

    override suspend fun encryptAes(value: ByteArray, initializationVector: ByteArray, encKey: ByteArray): ByteArray {
        return AES.encryptAesCbc(data = value, key = encKey, iv = initializationVector, padding = Padding.PKCS7Padding)
    }

    override suspend fun decryptAes(value: ByteArray, initializationVector: ByteArray, encKey: ByteArray): ByteArray {
        return AES.decryptAesCbc(data = value, key = encKey, iv = initializationVector, padding = Padding.PKCS7Padding)
    }

    override suspend fun hmac(macData: ByteArray, macKey: ByteArray, hashAlgorithm: CryptoHashAlgorithm): ByteArray {
        return when (hashAlgorithm) {
            CryptoHashAlgorithm.SHA1 -> HMAC.hmacSHA1(macKey, macData)
            CryptoHashAlgorithm.SHA256 -> HMAC.hmacSHA256(macKey, macData)
            CryptoHashAlgorithm.SHA512 -> TODO("HMAC for SHA512 is not supported yet")
            else -> TODO("$hashAlgorithm is not supported for mac")
        }.bytes
    }

}