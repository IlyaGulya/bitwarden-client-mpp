package me.gulya.bitwarden.crypto

import java.security.KeyPairGenerator
import java.security.SecureRandom

actual object PlatformCryptoPrimitives {

    private val random = SecureRandom()

    actual fun generateRsaKeyPair(length: RsaKeyLength): AsymmetricKeyPair {
        val keyGenerator = KeyPairGenerator.getInstance("RSA")
        keyGenerator.initialize(length.keyLength)
        return keyGenerator.generateKeyPair().run {
            AsymmetricKeyPair(
                public = this.public.encoded,
                private = this.private.encoded
            )
        }
    }

    actual fun randomBytes(numBytes: Int): ByteArray {
        return random.generateSeed(numBytes)
    }

}