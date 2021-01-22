package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DomainsResponse(
    @SerialName("EquivalentDomains") val equivalentDomains: List<List<String>>?,
    @SerialName("GlobalEquivalentDomains") val globalEquivalentDomains: List<GlobalDomainResponse>?
)