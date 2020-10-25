package me.gulya.bitwarden.enums

enum class TransactionType(
    override val value: Byte,
) : Valued<Byte> {
    Charge(0),
    Credit(1),
    PromotionalCredit(2),
    ReferralCredit(3),
    Refund(4);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}