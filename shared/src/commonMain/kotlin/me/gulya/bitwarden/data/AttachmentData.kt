package me.gulya.bitwarden.data

import me.gulya.bitwarden.server.response.AttachmentResponse

class AttachmentData(
    val id: String,
    val url: String?,
    val fileName: String,
    val key: String,
    val size: String?,
    val sizeName: String?,
) {
    constructor(response: AttachmentResponse) : this(
        id = response.id,
        url = response.url,
        fileName = response.fileName,
        key = response.key,
        size = response.size,
        sizeName = response.sizeName,
    )
}