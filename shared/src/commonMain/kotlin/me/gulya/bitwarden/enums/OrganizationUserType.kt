package me.gulya.bitwarden.enums

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