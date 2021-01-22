package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = FieldTypeSerializer::class)
enum class FieldType(
    override val value: Byte,
) : Valued<Byte> {
    Text(0),
    Hidden(1),
    Boolean(2);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }

}

class FieldTypeSerializer : ByteEnumTypeSerializer<FieldType>(
    valueFactory = FieldType.Companion::forValue
)