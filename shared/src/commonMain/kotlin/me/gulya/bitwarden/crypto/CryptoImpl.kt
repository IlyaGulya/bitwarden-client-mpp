package me.gulya.bitwarden.crypto

import com.soywiz.krypto.encoding.base64
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.enums.CryptoHashAlgorithm
import me.gulya.bitwarden.enums.KeyDerivationFunctionType
import okio.ByteString.Companion.toByteString

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

    override suspend fun encrypt(value: ByteArray, key: SymmetricCryptoKey) {
        val encryptedValue =
    }

    override suspend fun createKeyPair(key: SymmetricCryptoKey): RsaKeyPair {
        val keyPair = cryptoFunctions.generateRsaKeyPair(RsaKeyLength.RSA2048)
        return keyPair.run {
            RsaKeyPair(
                publicKey = public.toByteString().base64(),
                privateKey = encrypt(private, key).
            )
        }
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

data class EncryptedValue(
    val key: SymmetricCryptoKey,
    val data: ByteArray,
    val initializationVector: ByteArray,
    val messageAuthCode: ByteArray?
)