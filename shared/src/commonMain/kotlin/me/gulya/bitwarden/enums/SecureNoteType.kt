package me.gulya.bitwarden.enums

enum class SecureNoteType(
    override val value: Byte,
) : Valued<Byte> {
    Generic(0);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}