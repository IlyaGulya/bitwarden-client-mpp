package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = KeyDerivationFunctionTypeSerializer::class)
enum class KeyDerivationFunctionType(
    override val value: Byte,
) : Valued<Byte> {
    PBKDF2_SHA256(0);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

class KeyDerivationFunctionTypeSerializer : ByteEnumTypeSerializer<KeyDerivationFunctionType>(
    valueFactory = KeyDerivationFunctionType.Companion::forValue
)