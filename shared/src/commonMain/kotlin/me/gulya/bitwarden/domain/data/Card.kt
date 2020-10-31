package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.CardData

data class Card(
    val cardholderName: EncryptedString?,
    val brand: EncryptedString?,
    val number: EncryptedString?,
    val expMonth: EncryptedString?,
    val expYear: EncryptedString?,
    val code: EncryptedString?,
) {

    constructor(obj: CardData) : this(
        cardholderName = obj.cardholderName.toCipherString(),
        brand = obj.brand.toCipherString(),
        number = obj.number.toCipherString(),
        expMonth = obj.expMonth.toCipherString(),
        expYear = obj.expYear.toCipherString(),
        code = obj.code.toCipherString(),
    )

    fun toCardData(): CardData {
        return CardData(
            cardholderName = cardholderName?.encryptedString,
            brand = brand?.encryptedString,
            number = number?.encryptedString,
            expMonth = expMonth?.encryptedString,
            expYear = expYear?.encryptedString,
            code = code?.encryptedString,
        )
    }
}