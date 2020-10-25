package me.gulya.bitwarden.server

import me.gulya.bitwarden.enums.SecureNoteType

data class SecureNoteApi(
    val type: SecureNoteType,
)