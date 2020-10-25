package me.gulya.bitwarden.server.response

import me.gulya.bitwarden.enums.OrganizationUserStatusType
import me.gulya.bitwarden.enums.OrganizationUserType

data class ProfileOrganizationResponse(
    val id: String?,
    val name: String?,
    val useGroups: Boolean,
    val useDirectory: Boolean,
    val useEvents: Boolean,
    val useTotp: Boolean,
    val use2fa: Boolean,
    val useApi: Boolean,
    val usersGetPremium: Boolean,
    val selfHost: Boolean,
    val seats: Int,
    val maxCollections: Int,
    val maxStorageGb: Short?,
    val key: String?,
    val status: OrganizationUserStatusType,
    val type: OrganizationUserType,
    val enabled: Boolean,
)