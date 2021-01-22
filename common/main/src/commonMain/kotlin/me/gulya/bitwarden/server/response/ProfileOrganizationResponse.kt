package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.gulya.bitwarden.enums.OrganizationUserStatusType
import me.gulya.bitwarden.enums.OrganizationUserType

@Serializable
data class ProfileOrganizationResponse(
    @SerialName("Id") val id: String?,
    @SerialName("Name") val name: String?,
    @SerialName("UseGroups") val useGroups: Boolean,
    @SerialName("UseDirectory") val useDirectory: Boolean,
    @SerialName("UseEvents") val useEvents: Boolean,
    @SerialName("UseTotp") val useTotp: Boolean,
    @SerialName("Use2fa") val use2fa: Boolean,
    @SerialName("UseApi") val useApi: Boolean,
    @SerialName("UsersGetPremium") val usersGetPremium: Boolean,
    @SerialName("SelfHost") val selfHost: Boolean,
    @SerialName("Seats") val seats: Int,
    @SerialName("MaxCollections") val maxCollections: Int,
    @SerialName("MaxStorageGb") val maxStorageGb: Short?,
    @SerialName("Key") val key: String?,
    @SerialName("Status") val status: OrganizationUserStatusType,
    @SerialName("Type") val type: OrganizationUserType,
    @SerialName("Enabled") val enabled: Boolean,
)