package me.gulya.bitwarden.enums

enum class OrganizationUserStatusType(
    override val value: Byte,
): Valued<Byte> {
    Invited(0),
    Accepted(1),
    Confirmed(2);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }

}