package me.gulya.bitwarden.enums

enum class KeyDerivationFunctionType(
    override val value: Byte,
): Valued<Byte> {
    PBKDF2_SHA256(0);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

