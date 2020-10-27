package me.gulya.bitwarden.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.gulya.bitwarden.enums.UriMatchType

@Serializable
data class ServerLoginUri(
    @SerialName("Uri") val uri: String?,
    @SerialName("Match") val match: UriMatchType?,
)