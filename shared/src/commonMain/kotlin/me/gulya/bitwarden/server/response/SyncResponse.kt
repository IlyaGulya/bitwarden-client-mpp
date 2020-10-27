package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SyncResponse(
    @SerialName("Profile") val profile: ProfileResponse?,
    @SerialName("Folders") val folders: List<FolderResponse>,
    @SerialName("Collections") val collections: List<CollectionDetailsResponse>,
    @SerialName("Ciphers") val ciphers: List<CipherResponse>,
    @SerialName("Domains") val domains: DomainsResponse? = null,
    @SerialName("Policies") val policies: List<PolicyResponse>,
)