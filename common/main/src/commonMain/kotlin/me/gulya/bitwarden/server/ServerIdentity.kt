package me.gulya.bitwarden.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerIdentity(
    @SerialName("Title") val title: String?,
    @SerialName("FirstName") val firstName: String?,
    @SerialName("MiddleName") val middleName: String?,
    @SerialName("LastName") val lastName: String?,
    @SerialName("Address1") val address1: String?,
    @SerialName("Address2") val address2: String?,
    @SerialName("Address3") val address3: String?,
    @SerialName("City") val city: String?,
    @SerialName("State") val state: String?,
    @SerialName("PostalCode") val postalCode: String?,
    @SerialName("Country") val country: String?,
    @SerialName("Company") val company: String?,
    @SerialName("Email") val email: String?,
    @SerialName("Phone") val phone: String?,
    @SerialName("SSN") val sSN: String?,
    @SerialName("Username") val username: String?,
    @SerialName("PassportNumber") val passportNumber: String?,
    @SerialName("LicenseNumber") val licenseNumber: String?,
)