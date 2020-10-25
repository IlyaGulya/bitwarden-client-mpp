package me.gulya.bitwarden.enums

enum class PaymentMethodType(
    override val value: Byte,
) : Valued<Byte> {
    Card(0),
    BankAccount(1),
    PayPal(2),
    BitPay(3),
    Credit(4),
    WireTransfer(5);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}