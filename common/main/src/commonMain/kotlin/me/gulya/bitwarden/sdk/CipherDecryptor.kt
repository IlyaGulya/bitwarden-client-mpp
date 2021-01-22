package me.gulya.bitwarden.sdk

import me.gulya.bitwarden.domain.data.Cipher
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.presentation.CipherView

class CipherDecryptor(
    private val encryptionInteractor: EncryptionInteractor,
    private val loginDecryptor: LoginDecryptor,
    private val secureNoteDecryptor: SecureNoteDecryptor,
    private val cardDecryptor: CardDecryptor,
    private val identityDecryptor: IdentityDecryptor,
    private val attachmentDecryptor: AttachmentDecryptor,
    private val fieldDecryptor: FieldDecryptor,
    private val passwordHistoryDecryptor: PasswordHistoryDecryptor,
) {
    suspend fun decrypt(cipher: Cipher, key: SymmetricCryptoKey): CipherView {
        return cipher.run {
            CipherView(
                id = id,
                organizationId = organizationId,
                folderId = folderId,
                name = encryptionInteractor.decryptUtf8(name, key),
                notes = encryptionInteractor.decryptUtf8(notes, key),
                type = type,
                favorite = favorite,
                organizationUseTotp = organizationUseTotp,
                edit = edit,
                viewPassword = viewPassword,
                revisionDate = revisionDate,
                localData = localData,
                login = login?.let { loginDecryptor.decrypt(login, key) },
                secureNote = secureNote?.let { secureNoteDecryptor.decrypt(secureNote, key) },
                card = card?.let { cardDecryptor.decrypt(card, key) },
                identity = identity?.let { identityDecryptor.decrypt(identity, key) },
                attachments = attachments.map { attachmentDecryptor.decrypt(it, key) },
                fields = fields.map { fieldDecryptor.decrypt(it, key) },
                passwordHistory = passwordHistory.map { passwordHistoryDecryptor.decrypt(it, key) },
                collectionIds = collectionIds,
                deletedDate = deletedDate,
            )
        }
    }
}