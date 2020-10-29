package me.gulya.bitwarden.crypto

import me.gulya.bitwarden.enums.CryptoHashAlgorithm
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.digests.SHA512Digest
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator
import org.bouncycastle.crypto.params.KeyParameter
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory
import java.security.KeyPairGenerator
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher

actual object PlatformCryptoPrimitives {
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
        val cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA1AndMGF1Padding")
        cipher.ini
        val keyGenerator = RSAKeyPairGenerator()
        keyGenerator.ini
        keyGenerator.initialize(RSAKeyGenerationParameters)
        return PlatformCryptoPrimitives
    }
}