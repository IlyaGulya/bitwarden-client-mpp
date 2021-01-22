package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PasswordHistoryResponse(
    @SerialName("Password") val password: String,
    @SerialName("LastUsedDate") val lastUsedDate: DateTimeContainer,
)