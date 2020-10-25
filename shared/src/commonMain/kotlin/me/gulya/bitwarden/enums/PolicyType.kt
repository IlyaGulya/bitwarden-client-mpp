package me.gulya.bitwarden.enums

enum class PolicyType(
    override val value: Byte,
) : Valued<Byte> {
    TwoFactorAuthentication(0),
    MasterPassword(1),
    PasswordGenerator(2);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}