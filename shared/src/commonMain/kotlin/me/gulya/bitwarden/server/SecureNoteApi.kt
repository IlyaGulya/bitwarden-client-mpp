package me.gulya.bitwarden.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.gulya.bitwarden.enums.SecureNoteType

@Serializable
data class SecureNoteApi(
    @SerialName("Type") val type: SecureNoteType,
)