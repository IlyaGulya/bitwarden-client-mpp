package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttachmentResponse(
    @SerialName("Id") val id: String,
    @SerialName("Url") val url: String?,
    @SerialName("FileName") val fileName: String,
    @SerialName("Key") val key: String,
    @SerialName("Size") val size: String?,
    @SerialName("SizeName") val sizeName: String?,
)