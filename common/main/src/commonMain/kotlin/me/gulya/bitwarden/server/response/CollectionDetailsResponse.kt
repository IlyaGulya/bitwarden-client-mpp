package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDetailsResponse(
    @SerialName("Id") val id: String?,
    @SerialName("OrganizationId") val organizationId: String?,
    @SerialName("Name") val name: String?,
    @SerialName("ExternalId") val externalId: String?,
    @SerialName("ReadOnly") val readOnly: Boolean,
)