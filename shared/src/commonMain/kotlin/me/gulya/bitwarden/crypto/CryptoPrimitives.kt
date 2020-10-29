package me.gulya.bitwarden.crypto

import com.soywiz.krypto.AES
import com.soywiz.krypto.Padding
import me.gulya.bitwarden.enums.CryptoHashAlgorithm

class CryptoPrimitives {
    fun pbkdf2(password: ByteArray, salt: ByteArray, hashAlgorithm: CryptoHashAlgorithm, iterations: Int): ByteArray {
        return PlatformCryptoPrimitives.pbkdf2(password, salt, hashAlgorithm, iterations)
    }

    fun generateRsaOaepSha1KeyPair(length: RsaKeyLength): AsymmetricKeyPair {
        return PlatformCryptoPrimitives.generateRsaOaepSha1KeyPair(length)
    }

    fun randomBytes(numBytes: Int): ByteArray {
        return PlatformCryptoPrimitives.randomBytes(numBytes)
    }

}

data class AsymmetricKeyPair(
    val public: ByteArray,
    val private: ByteArray
)