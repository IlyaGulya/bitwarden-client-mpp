package me.gulya.bitwarden.crypto

expect object PlatformCryptoPrimitives {
    fun generateRsaKeyPair(length: RsaKeyLength): AsymmetricKeyPair
    fun randomBytes(numBytes: Int): ByteArray
}