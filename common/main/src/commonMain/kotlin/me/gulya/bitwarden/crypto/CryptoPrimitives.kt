package me.gulya.bitwarden.crypto

class CryptoPrimitives {

    fun generateRsaKeyPair(length: RsaKeyLength): AsymmetricKeyPair {
        return PlatformCryptoPrimitives.generateRsaKeyPair(length)
    }

    fun randomBytes(numBytes: Int): ByteArray {
        return PlatformCryptoPrimitives.randomBytes(numBytes)
    }

}