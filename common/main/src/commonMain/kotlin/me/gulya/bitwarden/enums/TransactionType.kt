package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = TransactionTypeSerializer::class)
enum class TransactionType(
    override val value: Byte,
) : Valued<Byte> {
    CHARGE(0),
    CREDIT(1),
    PROMOTIONAL_CREDIT(2),
    REFERRAL_CREDIT(3),
    REFUND(4);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

class TransactionTypeSerializer : ByteEnumTypeSerializer<TransactionType>(
    valueFactory = TransactionType.Companion::forValue
)