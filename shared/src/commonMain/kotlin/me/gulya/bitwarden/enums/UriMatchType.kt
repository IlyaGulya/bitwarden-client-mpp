package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = UriMatchTypeSerializer::class)
enum class UriMatchType(
    override val value: Byte,
) : Valued<Byte> {
    DOMAIN(0),
    HOST(1),
    STARTS_WITH(2),
    EXACT(3),
    REGULAR_EXPRESSION(4),
    NEVER(5);

    companion object {
        fun forValue(value: Byte) = UriMatchType.values().findValue(value)
    }
}

class UriMatchTypeSerializer : ByteEnumTypeSerializer<UriMatchType>(
    valueFactory = UriMatchType.Companion::forValue
)