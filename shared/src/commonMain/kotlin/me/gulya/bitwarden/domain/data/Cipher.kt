package me.gulya.bitwarden.domain.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.data.CipherData
import me.gulya.bitwarden.data.ifType
import me.gulya.bitwarden.enums.CipherType


class Cipher(
    val id: String? = null,
    val organizationId: String? = null,
    val folderId: String? = null,
    val name: EncryptedString? = null,
    val notes: EncryptedString? = null,
    val type: CipherType = CipherType.values().get(0),
    val favorite: Boolean = false,
    val organizationUseTotp: Boolean = false,
    val edit: Boolean = false,
    val viewPassword: Boolean = false,
    val revisionDate: DateTime = DateTime.EPOCH,
    val localData: Map<String, Any>,
    val login: Login?,
    val secureNote: SecureNote?,
    val card: Card?,
    val identity: Identity?,
    val attachments: List<Attachment>,
    val fields: List<Field>,
    val passwordHistory: List<PasswordHistory>,
    val collectionIds: Set<String>,
    val deletedDate: DateTime?,
) {

    constructor(
        cipherData: CipherData,
        localData: Map<String, Any>? = emptyMap()
    ) : this(
        id = cipherData.id,
        organizationId = cipherData.organizationId,
        folderId = cipherData.folderId,
        name = cipherData.name.toCipherString(),
        notes = cipherData.notes.toCipherString(),
        type = cipherData.type,
        favorite = cipherData.favorite,
        organizationUseTotp = cipherData.organizationUseTotp,
        edit = cipherData.edit,
        viewPassword = cipherData.viewPassword,
        revisionDate = cipherData.revisionDate,
        localData = localData ?: emptyMap(),
        login = cipherData.ifType(CipherType.Login) { login?.let(::Login) },
        secureNote = cipherData.ifType(CipherType.SecureNote) { secureNote?.let(::SecureNote) },
        card = cipherData.ifType(CipherType.Card) { card?.let(::Card) },
        identity = cipherData.ifType(CipherType.Identity) { identity?.let(::Identity) },
        attachments = cipherData.attachments.map(::Attachment),
        fields = cipherData.fields.map(::Field),
        passwordHistory = cipherData.passwordHistory.map(::PasswordHistory),
        collectionIds = HashSet(cipherData.collectionIds),
        deletedDate = cipherData.deletedDate,
    )

}

fun <T> Cipher.ifType(type: CipherType, getter: Cipher.() -> T?): T? {
    return if (this.type == type) {
        getter(this)
    } else null
}