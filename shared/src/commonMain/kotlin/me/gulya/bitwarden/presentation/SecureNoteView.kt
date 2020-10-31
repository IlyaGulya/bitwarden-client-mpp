package me.gulya.bitwarden.presentation

import me.gulya.bitwarden.enums.SecureNoteType

data class SecureNoteView(
    val type: SecureNoteType,
) {

    val subTitle: String?
        get() = null
}