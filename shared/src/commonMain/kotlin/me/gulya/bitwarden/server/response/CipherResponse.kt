package me.gulya.bitwarden.server.response

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.enums.CipherType
import me.gulya.bitwarden.server.SecureNoteApi
import me.gulya.bitwarden.server.ServerCard
import me.gulya.bitwarden.server.ServerField
import me.gulya.bitwarden.server.ServerIdentity
import me.gulya.bitwarden.server.ServerLogin

data class CipherResponse(
    val id: String?,
    val organizationId: String?,
    val folderId: String?,
    val type: CipherType = CipherType.values()[0],
    val name: String?,
    val notes: String?,
    val fields: List<ServerField>,
    val login: ServerLogin?,
    val card: ServerCard?,
    val identity: ServerIdentity?,
    val secureNote: SecureNoteApi?,
    val favorite: Boolean = false,
    val edit: Boolean = false,
    val viewPassword: Boolean = true,
    val organizationUseTotp: Boolean = false,
    val revisionDate: DateTime = DateTime.EPOCH,
    val attachments: List<AttachmentResponse>,
    val passwordHistory: List<PasswordHistoryResponse>,
    val collectionIds: List<String>,
    val deletedDate: DateTime?,
)

fun <T> CipherResponse.ifType(type: CipherType, getter: CipherResponse.() -> T): T? {
    return if (this.type == type) {
        // Added to address Issue (https://github.com/bitwarden/mobile/issues/1006)
        try {
            getter(this)
        } catch (e: Exception) {
            println("BitWarden CipherData constructor failed to initialize CyperType. id = $id")
            null
        }
    } else null
}