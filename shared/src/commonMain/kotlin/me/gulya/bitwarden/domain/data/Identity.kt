package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.IdentityData


class Identity(
    val title: EncryptedString?,
    val firstName: EncryptedString?,
    val middleName: EncryptedString?,
    val lastName: EncryptedString?,
    val address1: EncryptedString?,
    val address2: EncryptedString?,
    val address3: EncryptedString?,
    val city: EncryptedString?,
    val state: EncryptedString?,
    val postalCode: EncryptedString?,
    val country: EncryptedString?,
    val company: EncryptedString?,
    val email: EncryptedString?,
    val phone: EncryptedString?,
    val sSN: EncryptedString?,
    val username: EncryptedString?,
    val passportNumber: EncryptedString?,
    val licenseNumber: EncryptedString?,
) {
    constructor(obj: IdentityData) : this(
        title = obj.title.toCipherString(),
        firstName = obj.firstName.toCipherString(),
        middleName = obj.middleName.toCipherString(),
        lastName = obj.lastName.toCipherString(),
        address1 = obj.address1.toCipherString(),
        address2 = obj.address2.toCipherString(),
        address3 = obj.address3.toCipherString(),
        city = obj.city.toCipherString(),
        state = obj.state.toCipherString(),
        postalCode = obj.postalCode.toCipherString(),
        country = obj.country.toCipherString(),
        company = obj.company.toCipherString(),
        email = obj.email.toCipherString(),
        phone = obj.phone.toCipherString(),
        sSN = obj.sSN.toCipherString(),
        username = obj.username.toCipherString(),
        passportNumber = obj.passportNumber.toCipherString(),
        licenseNumber = obj.licenseNumber.toCipherString(),
    )

    fun toIdentityData(): IdentityData {
        return IdentityData(
            title = title?.encryptedString,
            firstName = firstName?.encryptedString,
            middleName = middleName?.encryptedString,
            lastName = lastName?.encryptedString,
            address1 = address1?.encryptedString,
            address2 = address2?.encryptedString,
            address3 = address3?.encryptedString,
            city = city?.encryptedString,
            state = state?.encryptedString,
            postalCode = postalCode?.encryptedString,
            country = country?.encryptedString,
            company = company?.encryptedString,
            email = email?.encryptedString,
            phone = phone?.encryptedString,
            sSN = sSN?.encryptedString,
            username = username?.encryptedString,
            passportNumber = passportNumber?.encryptedString,
            licenseNumber = licenseNumber?.encryptedString,
        )
    }

}