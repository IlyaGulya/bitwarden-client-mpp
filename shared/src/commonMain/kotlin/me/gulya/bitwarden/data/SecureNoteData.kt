package me.gulya.bitwarden.data

import me.gulya.bitwarden.enums.SecureNoteType
import me.gulya.bitwarden.server.SecureNoteApi

data class SecureNoteData(
    val type: SecureNoteType = SecureNoteType.values()[0]
) {
    constructor(data: SecureNoteApi) : this(
        type = data.type
    )
}