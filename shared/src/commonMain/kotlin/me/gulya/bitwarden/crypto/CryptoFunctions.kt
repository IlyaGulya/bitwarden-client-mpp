package me.gulya.bitwarden.crypto

import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import me.gulya.bitwarden.enums.CryptoHashAlgorithm

interface CryptoFunctions {
    suspend fun pbkdf2(password: String, salt: String, hashAlgorithm: CryptoHashAlgorithm, iterations: Int): ByteArray =
        pbkdf2(
            password = normalizePassword(password = password).toByteArray(Charsets.UTF_8),
            salt = salt.toByteArray(Charsets.UTF_8),
            hashAlgorithm = hashAlgorithm,
            iterations = iterations
        )

    suspend fun pbkdf2(
        password: ByteArray,
        salt: String,
        hashAlgorithm: CryptoHashAlgorithm,
        iterations: Int
    ): ByteArray =
        pbkdf2(
            password = password,
            salt = salt.toByteArray(Charsets.UTF_8),
            hashAlgorithm = hashAlgorithm,
            iterations = iterations
        )

    suspend fun pbkdf2(
        password: String,
        salt: ByteArray,
        hashAlgorithm: CryptoHashAlgorithm,
        iterations: Int
    ): ByteArray =
        pbkdf2(
            password = normalizePassword(password).toByteArray(Charsets.UTF_8),
            salt = salt,
            hashAlgorithm = hashAlgorithm,
            iterations = iterations
        )

    suspend fun pbkdf2(
        password: ByteArray,
        salt: ByteArray,
        hashAlgorithm: CryptoHashAlgorithm,
        iterations: Int
    ): ByteArray

    suspend fun generateRsaKeyPair(length: RsaKeyLength): AsymmetricKeyPair

    suspend fun randomBytes(numBytes: Int): ByteArray

    suspend fun encryptAes(value: ByteArray, initializationVector: ByteArray, encKey: ByteArray): ByteArray

    suspend fun decryptAes(value: ByteArray, initializationVector: ByteArray, encKey: ByteArray): ByteArray

    suspend fun hmac(macData: ByteArray, macKey: ByteArray, hashAlgorithm: CryptoHashAlgorithm): ByteArray

}