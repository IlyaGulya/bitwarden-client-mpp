package me.gulya.bitwarden.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerCard(
    @SerialName("CardholderName") val cardholderName: String?,
    @SerialName("Brand") val brand: String?,
    @SerialName("Number") val number: String?,
    @SerialName("ExpMonth") val expMonth: String?,
    @SerialName("ExpYear") val expYear: String?,
    @SerialName("Code") val code: String?,
)