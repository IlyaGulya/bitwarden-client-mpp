package me.gulya.bitwarden.server

data class ServerCard(
    val cardholderName: String?,
    val brand: String?,
    val number: String?,
    val expMonth: String?,
    val expYear: String?,
    val code: String?,
)