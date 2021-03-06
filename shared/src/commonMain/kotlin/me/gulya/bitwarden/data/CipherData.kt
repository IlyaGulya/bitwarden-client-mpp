package me.gulya.bitwarden.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.enums.CipherType
import me.gulya.bitwarden.server.response.CipherResponse
import me.gulya.bitwarden.server.response.ifType

class CipherData(
    val id: String? = null,
    val organizationId: String? = null,
    val folderId: String? = null,
    val userId: String? = null,
    val edit: Boolean = false,
    val viewPassword: Boolean = true,
    val organizationUseTotp: Boolean = false,
    val favorite: Boolean = false,
    val revisionDate: DateTime = DateTime.EPOCH,
    val type: CipherType = CipherType.values()[0],
    val name: String? = null,
    val notes: String?,
    val login: LoginData?,
    val secureNote: SecureNoteData?,
    val card: CardData?,
    val identity: IdentityData?,
    val fields: List<FieldData>,
    val attachments: List<AttachmentData>,
    val passwordHistory: List<PasswordHistoryData>,
    val collectionIds: List<String>,
    val deletedDate: DateTime?,
) {
    constructor(response: CipherResponse, userId: String? = null, collectionIds: Set<String>? = null) : this(
        id = response.id,
        organizationId = response.organizationId,
        folderId = response.folderId,
        userId = userId,
        edit = response.edit,
        viewPassword = response.viewPassword,
        organizationUseTotp = response.organizationUseTotp,
        favorite = response.favorite,
        revisionDate = response.revisionDate.dateTime,
        type = response.type,
        name = response.name,
        notes = response.notes,
        collectionIds = collectionIds?.toList() ?: response.collectionIds,
        login = response.ifType(CipherType.Login) { login?.let { LoginData(login) } },
        secureNote = response.ifType(CipherType.SecureNote) { secureNote?.let { SecureNoteData(secureNote) } },
        card = response.ifType(CipherType.Card) { card?.let { CardData(card) } },
        identity = response.ifType(CipherType.Identity) { identity?.let { IdentityData(identity) } },
        fields = (response.fields ?: emptyList()).map { FieldData(it) },
        attachments = (response.attachments ?: emptyList()).map { AttachmentData(it) },
        passwordHistory = (response.passwordHistory ?: emptyList()).map { PasswordHistoryData(it) },
        deletedDate = response.deletedDate?.dateTime,
    )
}

fun <T> CipherData.ifType(type: CipherType, getter: CipherData.() -> T): T? {
    return if (this.type == type) { getter(this) } else null
}