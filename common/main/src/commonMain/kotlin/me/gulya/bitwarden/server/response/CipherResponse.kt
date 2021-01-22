package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.gulya.bitwarden.enums.CipherType
import me.gulya.bitwarden.server.*

@Serializable
data class CipherResponse(
    @SerialName("Id") val id: String?,
    @SerialName("OrganizationId") val organizationId: String?,
    @SerialName("FolderId") val folderId: String?,
    @SerialName("Type") val type: CipherType = CipherType.values()[0],
    @SerialName("Name") val name: String?,
    @SerialName("Notes") val notes: String?,
    @SerialName("Fields") val fields: List<ServerField>? = null,
    @SerialName("Login") val login: ServerLogin? = null,
    @SerialName("Card") val card: ServerCard? = null,
    @SerialName("Identity") val identity: ServerIdentity? = null,
    @SerialName("SecureNote") val secureNote: SecureNoteApi? = null,
    @SerialName("Favorite") val favorite: Boolean = false,
    @SerialName("Edit") val edit: Boolean = false,
    @SerialName("ViewPassword") val viewPassword: Boolean = true,
    @SerialName("OrganizationUseTotp") val organizationUseTotp: Boolean = false,
    @SerialName("RevisionDate") val revisionDate: DateTimeContainer = DateTimeContainer.EPOCH,
    @SerialName("Attachments") val attachments: List<AttachmentResponse>? = null,
    @SerialName("PasswordHistory") val passwordHistory: List<PasswordHistoryResponse>? = null,
    @SerialName("CollectionIds") val collectionIds: List<String>,
    @SerialName("DeletedDate") val deletedDate: DateTimeContainer?,
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