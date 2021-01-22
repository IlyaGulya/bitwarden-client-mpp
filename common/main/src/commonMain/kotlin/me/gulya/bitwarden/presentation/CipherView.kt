package me.gulya.bitwarden.presentation

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.enums.CipherType

data class CipherView(
    val id: String? = null,
    val organizationId: String? = null,
    val folderId: String? = null,
    val name: String? = null,
    val notes: String? = null,
    val type: CipherType = CipherType.values()[0],
    val favorite: Boolean = false,
    val organizationUseTotp: Boolean = false,
    val edit: Boolean = false,
    val viewPassword: Boolean = true,
    val localData: Map<String, Any>? = null,
    val login: LoginView? = null,
    val identity: IdentityView? = null,
    val card: CardView? = null,
    val secureNote: SecureNoteView? = null,
    val attachments: List<AttachmentView>? = null,
    val fields: List<FieldView>,
    val passwordHistory: List<PasswordHistoryView>,
    val collectionIds: Set<String>,
    val revisionDate: DateTime,
    val deletedDate: DateTime?,
) {

    val subTitle: String?
        get() = when (type) {
            CipherType.Login -> login?.subTitle
            CipherType.SecureNote -> secureNote?.subTitle
            CipherType.Card -> card?.subTitle
            CipherType.Identity -> identity?.subTitle
        }

    val shared = { organizationId != null }
    val hasPasswordHistory = { passwordHistory.any() }
    val hasAttachments = { attachments?.any() ?: false }
    val hasOldAttachments = { attachments?.any { it.key == null } ?: false }
    val hasFields = { fields.any() }
    val passwordRevisionDisplayDate = {
        if (type != CipherType.Login || login == null) {
            null
        } else if (login.password.isNullOrBlank()) {
            null
        } else {
            login.passwordRevisionDate
        }
    }
    val isDeleted = { deletedDate != null && deletedDate != DateTime.EPOCH }

}