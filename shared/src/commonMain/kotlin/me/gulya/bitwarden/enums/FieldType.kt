package me.gulya.bitwarden.enums

enum class FieldType(
    override val value: Byte,
): Valued<Byte> {
    Text(0),
    Hidden(1),
    Boolean(2);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }

}