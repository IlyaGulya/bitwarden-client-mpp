package me.gulya.bitwarden.crypto

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

        return primitives.pbkdf2(
            password = password,
            salt = salt,
            hashAlgorithm = hashAlgorithm,
            iterations = iterations
        )
    }


}