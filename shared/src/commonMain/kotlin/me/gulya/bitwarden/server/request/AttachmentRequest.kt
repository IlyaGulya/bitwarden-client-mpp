package me.gulya.bitwarden.server.request

data class AttachmentRequest(
    val fileName: String,
    val key: String,
)