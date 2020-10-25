package me.gulya.bitwarden.crypto

import me.gulya.bitwarden.enums.CryptoHashAlgorithm

interface CryptoPrimitives {
    fun pbkdf2(password: ByteArray, salt: ByteArray, hashAlgorithm: CryptoHashAlgorithm, iterations: Int): ByteArray
}