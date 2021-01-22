package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import me.gulya.bitwarden.enums.PolicyType

@Serializable
data class PolicyResponse(
    @SerialName("Id") val id: String,
    @SerialName("OrganizationId") val organizationId: String?,
    @SerialName("Type") val type: PolicyType,
    @SerialName("Data") val data: Map<String, JsonElement>,
    @SerialName("Enabled") val enabled: Boolean,
)