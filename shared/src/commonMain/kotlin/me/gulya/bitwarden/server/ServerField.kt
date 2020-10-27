package me.gulya.bitwarden.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.gulya.bitwarden.enums.FieldType

@Serializable
data class ServerField(
    @SerialName("Type") val type: FieldType,
    @SerialName("Name") val name: String?,
    @SerialName("Value") val value: String?,
)