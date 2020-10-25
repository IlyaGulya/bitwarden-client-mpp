package me.gulya.bitwarden.crypto

import com.soywiz.krypto.encoding.base64
import me.gulya.bitwarden.domain.SymmetricCryptoKey
import me.gulya.bitwarden.enums.CryptoHashAlgorithm


interface Crypto {

    suspend fun hashPassword(password: String, key: SymmetricCryptoKey): String

}

class CryptoImpl(
    private val cryptoFunctions: CryptoFunctions
) : Crypto {
    override suspend fun hashPassword(password: String, key: SymmetricCryptoKey): String {
        return cryptoFunctions.pbkdf2(key.key, password, CryptoHashAlgorithm.Sha256, 1).base64
    }


}