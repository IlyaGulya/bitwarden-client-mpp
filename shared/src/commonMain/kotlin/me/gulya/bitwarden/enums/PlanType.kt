package me.gulya.bitwarden.enums

enum class PlanType(
    override val value: Byte,
) : Valued<Byte> {
    Free(0),
    FamiliesAnnually(1),
    TeamsMonthly(2),
    TeamsAnnually(3),
    EnterpriseMonthly(4),
    EnterpriseAnnually(5),
    Custom(6);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}