package me.gulya.bitwarden.data

import me.gulya.bitwarden.server.ServerCard

data class CardData(
    val cardholderName: String?,
    val brand: String?,
    val number: String?,
    val expMonth: String?,
    val expYear: String?,
    val code: String?,
) {
    constructor(data: ServerCard) : this(
        cardholderName = data.cardholderName,
        brand = data.brand,
        number = data.number,
        expMonth = data.expMonth,
        expYear = data.expYear,
        code = data.code,
    )

}