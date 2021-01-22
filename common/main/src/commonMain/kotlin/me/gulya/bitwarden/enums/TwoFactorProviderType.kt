package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = TwoFactorProviderTypeSerializer::class)
enum class TwoFactorProviderType(
    override val value: Byte,
) : Valued<Byte> {
    AUTHENTICATOR(0),
    EMAIL(1),
    DUO(2),
    YUBIKEY(3),
    U2F(4),
    REMEMBER(5),
    ORGANIZATION_DUO(6);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

class TwoFactorProviderTypeSerializer : ByteEnumTypeSerializer<TwoFactorProviderType>(
    valueFactory = TwoFactorProviderType.Companion::forValue
)