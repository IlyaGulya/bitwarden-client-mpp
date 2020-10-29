package me.gulya.bitwarden.crypto

import me.gulya.bitwarden.enums.CryptoHashAlgorithm
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.digests.SHA512Digest
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator
import org.bouncycastle.crypto.params.KeyParameter
import java.security.KeyPairGenerator
import java.security.SecureRandom

actual object PlatformCryptoPrimitives {

    val random = SecureRandom()

    actual fun pbkdf2(
        password: ByteArray,
        salt: ByteArray,
        hashAlgorithm: CryptoHashAlgorithm,
        iterations: Int
    ): ByteArray {
        val (digest, keySize) = when (hashAlgorithm) {
            CryptoHashAlgorithm.SHA256 -> SHA256Digest() to 256
            CryptoHashAlgorithm.SHA512 -> SHA512Digest() to 512
            else -> throw IllegalArgumentException("Digest $hashAlgorithm is not supported by PBKDF2")
        }

        val generator = PKCS5S2ParametersGenerator(digest)
        generator.init(password, salt, iterations)
        val parameters = generator.generateDerivedMacParameters(keySize)
        return (parameters as KeyParameter).key
    }

    actual fun generateRsaOaepSha1KeyPair(length: RsaKeyLength): AsymmetricKeyPair {
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