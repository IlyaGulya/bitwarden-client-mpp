package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.Card
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.CardView

class CardDecryptor(
    private val encryptionInteractor: EncryptionInteractor,
) {

    suspend fun decrypt(card: Card, key: SymmetricCryptoKey): CardView {
        return card.run {
            CardView(
                cardholderName = encryptionInteractor.decryptUtf8(cardholderName, key),
                brand = encryptionInteractor.decryptUtf8(brand, key),
                number = encryptionInteractor.decryptUtf8(number, key),
                expMonth = encryptionInteractor.decryptUtf8(expMonth, key),
                expYear = encryptionInteractor.decryptUtf8(expYear, key),
                code = encryptionInteractor.decryptUtf8(code, key),
            )
        }
    }

}