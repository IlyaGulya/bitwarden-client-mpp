package me.gulya.bitwarden.enums

enum class TwoFactorProviderType(
    override val value: Byte,
) : Valued<Byte> {
    Authenticator(0),
    Email(1),
    Duo(2),
    YubiKey(3),
    U2f(4),
    Remember(5),
    OrganizationDuo(6);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}