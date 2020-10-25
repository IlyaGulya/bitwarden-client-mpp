package me.gulya.bitwarden.enums

enum class UriMatchType(
    override val value: Byte,
) : Valued<Byte> {
    Domain(0),
    Host(1),
    StartsWith(2),
    Exact(3),
    RegularExpression(4),
    Never(5);

    companion object {
        fun forValue(value: Byte) = TwoFactorProviderType.values().findValue(value)
    }
}