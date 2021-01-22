package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.Identity
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.IdentityView

class IdentityDecryptor(
    private val encryptionInteractor: EncryptionInteractor,
) {
    suspend fun decrypt(identity: Identity, key: SymmetricCryptoKey): IdentityView {
        return identity.run {
            IdentityView(
                title = encryptionInteractor.decryptUtf8(title, key),
                firstName = encryptionInteractor.decryptUtf8(firstName, key),
                middleName = encryptionInteractor.decryptUtf8(middleName, key),
                lastName = encryptionInteractor.decryptUtf8(lastName, key),
                address1 = encryptionInteractor.decryptUtf8(address1, key),
                address2 = encryptionInteractor.decryptUtf8(address2, key),
                address3 = encryptionInteractor.decryptUtf8(address3, key),
                city = encryptionInteractor.decryptUtf8(city, key),
                state = encryptionInteractor.decryptUtf8(state, key),
                postalCode = encryptionInteractor.decryptUtf8(postalCode, key),
                country = encryptionInteractor.decryptUtf8(country, key),
                company = encryptionInteractor.decryptUtf8(company, key),
                email = encryptionInteractor.decryptUtf8(email, key),
                phone = encryptionInteractor.decryptUtf8(phone, key),
                sSN = encryptionInteractor.decryptUtf8(sSN, key),
                username = encryptionInteractor.decryptUtf8(username, key),
                passportNumber = encryptionInteractor.decryptUtf8(passportNumber, key),
                licenseNumber = encryptionInteractor.decryptUtf8(licenseNumber, key),
            )
        }
    }
}