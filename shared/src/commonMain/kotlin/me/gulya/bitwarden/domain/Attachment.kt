package me.gulya.bitwarden.domain

import bit.core.abstractions.ICryptoService
import bit.core.utilities.ServiceContainer.Resolve
import me.gulya.bitwarden.data.AttachmentData
import me.gulya.bitwarden.presentation.AttachmentView

class Attachment(
    val id: String,
    val url: String?,
    val size: String?,
    val sizeName: String?,
    val key: EncryptedString,
    val fileName: EncryptedString,
) {
    constructor(obj: AttachmentData) : this(
        id = obj.id,
        url = obj.url,
        sizeName = obj.sizeName,
        size = obj.size,
        fileName = obj.fileName.toCipherString(),
        key = obj.key.toCipherString()
    )

    suspend fun decrypt(orgId: String?): AttachmentView {
        // TODO extension for EncryptedString?
        val key = if (key != null) {
            val cryptoService = Resolve<ICryptoService>("cryptoService")
            try {
                val orgKey: SymmetricCryptoKey = cryptoService.GetOrgKeyAsync(orgId)
                val decValue: ByteArray = cryptoService.DecryptToBytesAsync(key, orgKey)
                SymmetricCryptoKey(decValue)
            } catch (e: Exception) {
                // TODO: error?
                null
            }
        } else {
            null
        }
        return AttachmentView(
            id = id,
            url = url,
            size = size,
            sizeName = sizeName,
            key = key,
            fileName = fileName?.decrypt(orgId),
        )

    }

    fun toAttachmentData(): AttachmentData {
        return AttachmentData(
            id = id,
            url = url,
            sizeName = sizeName,
            fileName = fileName?.encryptedString,
            key = key?.encryptedString,
            size = size,
        )
    }
}