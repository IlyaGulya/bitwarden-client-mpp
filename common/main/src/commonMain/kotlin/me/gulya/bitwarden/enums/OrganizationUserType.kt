package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = OrganizationUserTypeSerializer::class)
enum class OrganizationUserType(
    override val value: Byte,
) : Valued<Byte> {
    Owner(0),
    Admin(1),
    User(2),
    Manager(3);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

class OrganizationUserTypeSerializer : ByteEnumTypeSerializer<OrganizationUserType>(
    valueFactory = OrganizationUserType.Companion::forValue
)