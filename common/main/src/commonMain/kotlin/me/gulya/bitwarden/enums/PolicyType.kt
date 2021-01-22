package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = PolicyTypeSerializer::class)
enum class PolicyType(
    override val value: Byte,
) : Valued<Byte> {
    TWO_FACTOR_AUTHENTICATION(0),
    MASTER_PASSWORD(1),
    PASSWORD_GENERATOR(2);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

class PolicyTypeSerializer : ByteEnumTypeSerializer<PolicyType>(
    valueFactory = PolicyType.Companion::forValue
)