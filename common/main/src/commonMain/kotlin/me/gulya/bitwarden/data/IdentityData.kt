package me.gulya.bitwarden.data

import me.gulya.bitwarden.server.ServerIdentity

data class IdentityData(
    val title: String?,
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
    val address1: String?,
    val address2: String?,
    val address3: String?,
    val city: String?,
    val state: String?,
    val postalCode: String?,
    val country: String?,
    val company: String?,
    val email: String?,
    val phone: String?,
    val sSN: String?,
    val username: String?,
    val passportNumber: String?,
    val licenseNumber: String?,
) {
    constructor(data: ServerIdentity) : this(
        title = data.title,
        firstName = data.firstName,
        middleName = data.middleName,
        lastName = data.lastName,
        address1 = data.address1,
        address2 = data.address2,
        address3 = data.address3,
        city = data.city,
        state = data.state,
        postalCode = data.postalCode,
        country = data.country,
        company = data.company,
        email = data.email,
        phone = data.phone,
        sSN = data.sSN,
        username = data.username,
        passportNumber = data.passportNumber,
        licenseNumber = data.licenseNumber,
    )
}