package me.gulya.bitwarden.crypto

import com.soywiz.krypto.encoding.base64
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.enums.CryptoHashAlgorithm
import me.gulya.bitwarden.enums.KeyDerivationFunctionType


interface Crypto {

    suspend fun hashPassword(password: String, key: SymmetricCryptoKey): String

    suspend fun createKey(password: String, salt: String, kdf: KeyDerivationFunctionType, iterations: Int): SymmetricCryptoKey

}

class CryptoImpl(
    private val cryptoFunctions: CryptoFunctions
) : Crypto {
    companion object {
        const val PBKDF2_MIN_ITERATIONS = 5000
    }

    override suspend fun hashPassword(password: String, key: SymmetricCryptoKey): String {
        return cryptoFunctions.pbkdf2(key.key, password, CryptoHashAlgorithm.SHA256, 1).base64
    }

    override suspend fun createKey(password: String, salt: String, kdf: KeyDerivationFunctionType, iterations: Int): SymmetricCryptoKey {
        if (iterations < PBKDF2_MIN_ITERATIONS) throw IllegalArgumentException("PBKDF2 has to have at least $PBKDF2_MIN_ITERATIONS iterations")
        val key = cryptoFunctions.pbkdf2(password, salt, CryptoHashAlgorithm.SHA256, iterations)
        return SymmetricCryptoKey(key)
    }

}