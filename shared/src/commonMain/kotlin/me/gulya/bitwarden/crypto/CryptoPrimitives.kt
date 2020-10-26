package me.gulya.bitwarden.crypto

import me.gulya.bitwarden.enums.CryptoHashAlgorithm

class CryptoPrimitives {
    fun pbkdf2(password: ByteArray, salt: ByteArray, hashAlgorithm: CryptoHashAlgorithm, iterations: Int): ByteArray {
        return PlatformCryptoPrimitives.pbkdf2(password, salt, hashAlgorithm, iterations)
    }
}