package me.gulya.bitwarden.server.response

data class AttachmentResponse(
    val id: String,
    val url: String?,
    val fileName: String,
    val key: String,
    val size: String?,
    val sizeName: String?,
)