package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.AttachmentData

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

    fun toAttachmentData(): AttachmentData {
        return AttachmentData(
            id = id,
            url = url,
            sizeName = sizeName,
            fileName = fileName.encryptedString,
            key = key.encryptedString,
            size = size,
        )
    }
}