package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = CryptoHashAlgorithmSerializer::class)
enum class CryptoHashAlgorithm(
    override val value: Byte,
): Valued<Byte> {
    SHA1(0),
    SHA256(1),
    SHA512(2),
    MD5(3);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }

}

class CryptoHashAlgorithmSerializer : ByteEnumTypeSerializer<CryptoHashAlgorithm>(
    valueFactory = CryptoHashAlgorithm.Companion::forValue
)