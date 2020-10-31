package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.SecureNoteData
import me.gulya.bitwarden.enums.SecureNoteType
import me.gulya.bitwarden.presentation.SecureNoteView
import me.gulya.bitwarden.sdk.EncryptionInteractor

data class SecureNote(
    val type: SecureNoteType
) {

    constructor(obj: SecureNoteData) : this(
        type = obj.type
    )

    suspend fun decrypt(encryptionInteractor: EncryptionInteractor, orgId: String?): SecureNoteView {
        return SecureNoteView(
            type = type,
        )
    }

    fun toSecureNoteData(): SecureNoteData = SecureNoteData(type)
}