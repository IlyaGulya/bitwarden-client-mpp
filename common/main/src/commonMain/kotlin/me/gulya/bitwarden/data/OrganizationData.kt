package me.gulya.bitwarden.data

import me.gulya.bitwarden.enums.OrganizationUserStatusType
import me.gulya.bitwarden.enums.OrganizationUserType
import me.gulya.bitwarden.server.response.ProfileOrganizationResponse

data class OrganizationData(
    val id: String?,
    val name: String?,
    val status: OrganizationUserStatusType,
    val type: OrganizationUserType,
    val enabled: Boolean,
    val useGroups: Boolean,
    val useDirectory: Boolean,
    val useEvents: Boolean,
    val useTotp: Boolean,
    val use2fa: Boolean,
    val useApi: Boolean,
    val selfHost: Boolean,
    val usersGetPremium: Boolean,
    val seats: Int,
    val maxCollections: Int,
    val maxStorageGb: Short?,
) {
    constructor(response: ProfileOrganizationResponse) : this(
        id = response.id,
        name = response.name,
        status = response.status,
        type = response.type,
        enabled = response.enabled,
        useGroups = response.useGroups,
        useDirectory = response.useDirectory,
        useEvents = response.useEvents,
        useTotp = response.useTotp,
        use2fa = response.use2fa,
        useApi = response.useApi,
        selfHost = response.selfHost,
        usersGetPremium = response.usersGetPremium,
        seats = response.seats,
        maxCollections = response.maxCollections,
        maxStorageGb = response.maxStorageGb,
    )

}