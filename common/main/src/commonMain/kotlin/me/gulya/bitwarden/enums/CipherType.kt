package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = CipherTypeSerializer::class)
enum class CipherType(
    override val value: Byte,
) : Valued<Byte> {
    // Folder is deprecated
    //Folder = 0,
    Login(1),
    SecureNote(2),
    Card(3),
    Identity(4);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }

}

class CipherTypeSerializer : ByteEnumTypeSerializer<CipherType>(
    valueFactory = CipherType.Companion::forValue
)