package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = SecureNoteTypeSerializer::class)
enum class SecureNoteType(
    override val value: Byte,
) : Valued<Byte> {
    GENERIC(0);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

class SecureNoteTypeSerializer : ByteEnumTypeSerializer<SecureNoteType>(
    valueFactory = SecureNoteType.Companion::forValue
)