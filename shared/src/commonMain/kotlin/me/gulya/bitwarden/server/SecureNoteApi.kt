package me.gulya.bitwarden.server

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.enums.SecureNoteType

@Serializable
data class SecureNoteApi(
    val type: SecureNoteType,
)