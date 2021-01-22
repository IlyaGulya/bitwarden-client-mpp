package me.gulya.bitwarden.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.gulya.bitwarden.server.response.DateTimeContainer

@Serializable
data class ServerLogin(
    @SerialName("Username") val username: String?,
    @SerialName("Password") val password: String?,
    @SerialName("PasswordRevisionDate") val passwordRevisionDate: DateTimeContainer? = null,
    @SerialName("Totp") val totp: String?,
    @SerialName("Uris") val uris: List<ServerLoginUri>? = null,
)