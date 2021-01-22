package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = PaymentMethodTypeSerializer::class)
enum class PaymentMethodType(
    override val value: Byte,
) : Valued<Byte> {
    CARD(0),
    BANK_ACCOUNT(1),
    PAYPAL(2),
    BITPAY(3),
    CREDIT(4),
    WIRE_TRANSFER(5);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

class PaymentMethodTypeSerializer : ByteEnumTypeSerializer<PaymentMethodType>(
    valueFactory = PaymentMethodType.Companion::forValue
)