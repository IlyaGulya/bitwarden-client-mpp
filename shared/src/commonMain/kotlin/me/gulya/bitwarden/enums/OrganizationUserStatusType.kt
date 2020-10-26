package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = OrganizationUserStatusTypeSerializer::class)
enum class OrganizationUserStatusType(
    override val value: Byte,
): Valued<Byte> {
    INVITED(0),
    ACCEPTED(1),
    CONFIRMED(2);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }

}

class OrganizationUserStatusTypeSerializer : ByteEnumTypeSerializer<OrganizationUserStatusType>(
    valueFactory = OrganizationUserStatusType.Companion::forValue
)