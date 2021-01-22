package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalDomainResponse(
    @SerialName("Type") val type: Int,
    @SerialName("Domains") val domains: List<String>?,
    @SerialName("Excluded") val excluded: Boolean,
)