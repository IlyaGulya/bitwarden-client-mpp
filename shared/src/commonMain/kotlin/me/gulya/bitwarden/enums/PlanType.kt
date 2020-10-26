package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = PlanTypeSerializer::class)
enum class PlanType(
    override val value: Byte,
) : Valued<Byte> {
    FREE(0),
    FAMILIES_ANNUALLY(1),
    TEAMS_MONTHLY(2),
    TEAMS_ANNUALLY(3),
    ENTERPRISE_MONTHLY(4),
    ENTERPRISE_ANNUALLY(5),
    CUSTOM(6);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

class PlanTypeSerializer : ByteEnumTypeSerializer<PlanType>(
    valueFactory = PlanType.Companion::forValue
)