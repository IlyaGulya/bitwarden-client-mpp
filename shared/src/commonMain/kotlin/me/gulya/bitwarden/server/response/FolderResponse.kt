package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FolderResponse(
    @SerialName("Id") val id: String,
    @SerialName("Name") val name: String,
    @SerialName("RevisionDate") val revisionDate: DateTimeContainer,
)