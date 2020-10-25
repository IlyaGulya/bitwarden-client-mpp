package me.gulya.bitwarden.domain

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.data.CipherData
import me.gulya.bitwarden.data.ifType
import me.gulya.bitwarden.enums.CipherType
import me.gulya.bitwarden.presentation.CipherView


class Cipher(
    var id: String? = null,
    var organizationId: String? = null,
    var folderId: String? = null,
    var name: EncryptedString? = null,
    var notes: EncryptedString? = null,
    var type: CipherType = CipherType.values().get(0),
    var favorite: Boolean = false,
    var organizationUseTotp: Boolean = false,
    var edit: Boolean = false,
    var viewPassword: Boolean = false,
    var revisionDate: DateTime = DateTime.EPOCH,
    var localData: Map<String, Any>,
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

    suspend fun decrypt(orgId: String?): CipherView {
        return CipherView(
            id = id,
            organizationId = organizationId,
            folderId = folderId,
            name = name?.decrypt(orgId),
            notes = notes?.decrypt(orgId),
            type = type,
            favorite = favorite,
            organizationUseTotp = organizationUseTotp,
            edit = edit,
            viewPassword = viewPassword,
            revisionDate = revisionDate,
            localData = localData,
            login = login?.decrypt(orgId),
            secureNote = secureNote?.decrypt(orgId),
            card = card?.decrypt(orgId),
            identity = identity?.decryptAsync(orgId),
            attachments = attachments.map { it.decrypt(orgId) },
            fields = fields.map { it.decrypt(orgId) },
            passwordHistory = passwordHistory.map { it.decrypt(orgId) },
            collectionIds = collectionIds,
            deletedDate = deletedDate,
        )
    }

}

fun <T> Cipher.ifType(type: CipherType, getter: Cipher.() -> T?): T? {
    return if (this.type == type) { getter(this) } else null
}